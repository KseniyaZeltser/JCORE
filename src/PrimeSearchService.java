import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PrimeSearchService {

    public static List<Integer> parallelPrimeSearch(
            List<Integer> numbers,
            int numThreads,
            long globalTimeoutSeconds,
            long taskTimeoutSeconds) throws Exception {

        // Разделение списка на части
        List<List<Integer>> partitions = new ArrayList<>();

        int chunkSize = (numbers.size() + numThreads - 1) / numThreads;

        for (int i = 0; i < numbers.size(); i += chunkSize) {
            int end = Math.min(numbers.size(), i + chunkSize);
            partitions.add(numbers.subList(i, end));
        }

        ExecutorService executor =
                Executors.newFixedThreadPool(numThreads);

        CompletionService<List<Integer>> completionService =
                new ExecutorCompletionService<>(executor);

        try {

            // Отправка задач
            List<Future<List<Integer>>> futures = new ArrayList<>();

            for (List<Integer> partition : partitions) {
                futures.add(
                        completionService.submit(
                                new PrimeSearchTask(partition)
                        )
                );
            }

            // Сбор результатов
            List<Integer> allPrimes = new ArrayList<>();

            long startTime = System.currentTimeMillis();
            long timeoutMs = globalTimeoutSeconds * 1000;

            for (int i = 0; i < futures.size(); i++) {

                if (System.currentTimeMillis() - startTime > timeoutMs) {
                    System.out.println(
                            "Превышено общее время выполнения"
                    );
                    break;
                }

                Future<List<Integer>> future =
                        completionService.poll(
                                taskTimeoutSeconds,
                                TimeUnit.SECONDS
                        );

                if (future == null) {

                    System.out.println(
                            "Таймаут - отмена оставшихся задач"
                    );

                    break;

                } else {

                    allPrimes.addAll(future.get());
                }
            }

            // Отмена оставшихся задач
            for (Future<List<Integer>> future : futures) {

                if (!future.isDone()) {
                    future.cancel(true);
                }
            }

            return allPrimes;

        } finally {

            executor.shutdown();
        }
    }
}