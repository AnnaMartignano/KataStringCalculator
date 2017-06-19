import static java.util.Arrays.*;

/**
 * Created by annamartignano on 07/06/17.
 */
public class StringCalculator {


    public static final char DEFAULT_DELIMITER = ',';
    public static final String REGEX_CUSTOM_DELIMITER_FORMAT0 = "//[^0-9]\n.+";
    public static final String REGEX_CUSTOM_DELIMITER_FORMAT1 = "/[^0-9]\n.+";

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
        if( existCustomDelimiter(numbers)){
            if(numbers.matches(REGEX_CUSTOM_DELIMITER_FORMAT0))
                return numbers.substring(2,3).charAt(0);
            else
                return numbers.substring(1,2).charAt(0);
        }
        else{
            return DEFAULT_DELIMITER;
        }
    }

    private boolean existCustomDelimiter(String numbers) {
        return (numbers.matches(REGEX_CUSTOM_DELIMITER_FORMAT0) ||
        numbers.matches(REGEX_CUSTOM_DELIMITER_FORMAT1));
    }

    private String numbersToEvaluate(String numbers) {
        if( existCustomDelimiter(numbers)){
            if(numbers.matches(REGEX_CUSTOM_DELIMITER_FORMAT0))
                return numbers.substring(4);
            else
                return numbers.substring(3);
        }

        return numbers;
    }

    private boolean existInvalidChars(String numbers) {
        return numbers.contains(",\n") || numbers.contains("\n,");
    }

    private String[] numbersFor(String numbers, char delimiter) {
        numbers = numbersToEvaluate(numbers);
        return numbers.split("[" + delimiter + "||\n]");
    }
}
