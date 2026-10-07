import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NumberGenerator {

    public static List<Integer> generateTestNumbers(int count) {

        List<Integer> numbers = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < count; i++) {
            numbers.add(random.nextInt(100) + 1);
        }

        return numbers;
    }
}