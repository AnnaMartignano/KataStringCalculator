import java.util.stream.IntStream;

import static java.util.Arrays.*;

/**
 * Created by annamartignano on 07/06/17.
 */
public class StringCalculator {


    public static final char DEFAULT_DELIMITER = ',';
    public static final String REGEX_CUSTOM_DELIMITER_EXTENDED = "//[^0-9]\n.+";
    public static final String REGEX_CUSTOM_DELIMITER_CONTRACT = "/[^0-9]\n.+";

    public Integer add(String numbers) throws CaratteriNonAmmessiException, NegativeNotAllowedException {
        if (numbers.isEmpty()) {
            return 0;
        }

        if (existInvalidChars(numbers)) {
            throw new CaratteriNonAmmessiException();
        }

        DelimiterFormat delimiterFormat = findDelimiterFormat(numbers);

        char delimiter = delimiterOf(numbers, delimiterFormat);
        int[] arrayOfNumbers = convertStringtoArray(numbers, delimiter, delimiterFormat);


        if (existNegativeNumber(arrayOfNumbers)) {
            throw new NegativeNotAllowedException(arrayOfNumbers);
        }

        return sum(arrayOfNumbers);

    }

    private int[] convertStringtoArray(String numbers, char delimiter, DelimiterFormat delimiterFormat) {
        numbers = numbersToEvaluate(numbers, delimiterFormat);
        String[] stringOfNumbers = numbers.split("[" + delimiter + "||\n]");
        return stream(stringOfNumbers).mapToInt(Integer::parseInt).toArray();
    }

    private int sum(int[] myArray) {
        myArray = ignore1000PlusNumber(myArray);
        return IntStream.of(myArray).sum();
    }

    private int[] ignore1000PlusNumber(int[] myArray) {
        for (int i = 0; i < myArray.length; i++) {
            if (myArray[i] > 1000) {
                myArray[i] = 0;
            }
        }
        return myArray;
    }

    private char delimiterOf(String numbers, DelimiterFormat delimiterFormat) {
        switch (delimiterFormat) {
            case None:
                return DEFAULT_DELIMITER;
            case Contract:
                return numbers.substring(1, 2).charAt(0);
            case Extended:
                return numbers.substring(2, 3).charAt(0);
            default:
                return DEFAULT_DELIMITER;
        }
    }

    private DelimiterFormat findDelimiterFormat(String numbers) {
        if (numbers.matches(REGEX_CUSTOM_DELIMITER_EXTENDED)) {
            return DelimiterFormat.Extended;
        } else if (numbers.matches(REGEX_CUSTOM_DELIMITER_CONTRACT)) {
            return DelimiterFormat.Contract;
        }
        else{
            return DelimiterFormat.None;
        }
    }

    private String numbersToEvaluate(String numbers, DelimiterFormat delimiterFormat) {
        switch (delimiterFormat) {
            case None:
                return numbers;
            case Contract:
                return numbers.substring(3);
            case Extended:
                return numbers.substring(4);
            default:
                return numbers;
        }
    }

    private boolean existInvalidChars(String numbers) {
        return numbers.contains(",\n") || numbers.contains("\n,");
    }

    private boolean existNegativeNumber(int[] myArray) {
        for (int i = 0; i < myArray.length; i++) {
            if (myArray[i] < 0) {
                return true;
            }
        }
        return false;
    }
}
