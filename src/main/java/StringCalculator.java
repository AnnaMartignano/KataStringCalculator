import static java.util.Arrays.*;

/**
 * Created by annamartignano on 07/06/17.
 */
public class StringCalculator {


    public static final char DEFAULT_DELIMITER = ',';

    public Integer add(String numbers) throws CaratteriNonAmmessiException {
        if (numbers.isEmpty()) {
            return 0;
        }

        if (existInvalidChars(numbers)) {
            throw new CaratteriNonAmmessiException();
        }

        //caso generico, eslude tutti i caratteri che non sono "digit" [^0-9]
        char delimiter = delimiterOf(numbers);
        return stream(numbersFor(numbers, delimiter))
                .mapToInt(Integer::parseInt)
                .sum();

    }

    private char delimiterOf(String numbers) {
        if(numbers.matches("//[^0-9]\n.+")) {
            return numbers.substring(2,3).charAt(0);
        }
        return DEFAULT_DELIMITER;
    }

    private String numbersToEvaluate(String numbers) {
        if(numbers.matches("//[^0-9]\n.+")) {
            return numbers.substring(4);
        }
        return numbers;
    }

    private boolean existInvalidChars(String numbers) {
        return numbers.contains(",\n") || numbers.contains("\n,");
    }

    private String[] numbersFor(String numbers, char delimiter) {
        numbers = numbersToEvaluate(numbers);
        //return numbers.split("[" + delimiter + "||\n]");
        return numbers.split("[" + delimiter + "||\n]");
    }
}
