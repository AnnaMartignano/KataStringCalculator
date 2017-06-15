import static java.util.Arrays.*;

/**
 * Created by annamartignano on 07/06/17.
 */
public class StringCalculator {


    public static final char DEFAULT_DELIMITER = ',';
    public static final String REGEX_CUSTOM_DELIMITER = "//[^0-9]\n.+";

    public Integer add(String numbers) throws CaratteriNonAmmessiException {
        if (numbers.isEmpty()) {
            return 0;
        }

        if (existInvalidChars(numbers)) {
            throw new CaratteriNonAmmessiException();
        }

        return sum(numbers, delimiterOf(numbers));

    }

    private int sum(String numbers, char delimiter) {
        return stream(numbersFor(numbers, delimiter))
                .mapToInt(Integer::parseInt)
                .sum();
    }

    private char delimiterOf(String numbers) {
        return existCustomDelimiter(numbers) ?
                numbers.substring(2, 3).charAt(0) :
                DEFAULT_DELIMITER;
    }

    private boolean existCustomDelimiter(String numbers) {
        return numbers.matches(REGEX_CUSTOM_DELIMITER);
    }

    private String numbersToEvaluate(String numbers) {
        return existCustomDelimiter(numbers) ?
                numbers.substring(4) :
                numbers;
    }

    private boolean existInvalidChars(String numbers) {
        return numbers.contains(",\n") || numbers.contains("\n,");
    }

    private String[] numbersFor(String numbers, char delimiter) {
        numbers = numbersToEvaluate(numbers);
        return numbers.split("[" + delimiter + "||\n]");
    }
}
