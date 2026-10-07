import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class PrimeSearchTask implements Callable<List<Integer>> {

    private final List<Integer> numbers;

    public PrimeSearchTask(List<Integer> numbers) {
        this.numbers = numbers;
    }

    @Override
    public List<Integer> call() {
        List<Integer> primes = new ArrayList<>();

        for (int number : numbers) {

            if (Thread.currentThread().isInterrupted()) {
                break;
            }

            if (PrimeChecker.isPrime(number)) {
                primes.add(number);
            }
        }

        return primes;
    }
}