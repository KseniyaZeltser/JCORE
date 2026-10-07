import java.util.List;

public class ParallelPrimeSearch {

    public static void main(String[] args) throws Exception {

        List<Integer> numbers =
                NumberGenerator.generateTestNumbers(100);

        System.out.println(
                "Сгенерировано " + numbers.size() + " чисел"
        );

        System.out.println(numbers);

        List<Integer> result =
                PrimeSearchService.parallelPrimeSearch(
                        numbers,
                        4,
                        10,
                        5
                );

        System.out.println(
                "Найдено простых чисел: " + result.size()
        );

        if (!result.isEmpty()) {

            System.out.println(
                    "Простые числа: " +
                            result.subList(
                                    0,
                                    Math.min(100, result.size())
                            )
            );
        }
    }
}