import java.util.stream.IntStream;

import static java.util.Arrays.*;

/**
 * Created by annamartignano on 07/06/17.
 */
public class StringCalculator {


    public static final String DEFAULT_DELIMITER = ",";
    public static final String ANY_CUSTOM_DELIMITER = "[^0-9 ^-]";
    public static final String REGEX_ONE_CUSTOM_DELIMITER_EXTENDED = "//[^0-9]\n.+";
    public static final String REGEX_ONE_CUSTOM_DELIMITER_CONTRACT = "/[^0-9]\n.+";
    public static final String REGEX_REPEATED_CUSTOM_DELIMITER = "//\\[[^0-9 ^\\]]{2,}\\][^\\[].+";
    public static final String REGEX_MULTIPLE_CUSTOM_DELIMITER = "//\\[[^0-9 ^\\]]{1,}\\]\\[[^0-9]{1,}\\].+";

    public Integer add(String numbers) throws CharacterNotAllowedException, NegativeNotAllowedException {
        if (numbers.isEmpty()) {
            return 0;
        }

        if (existInvalidChars(numbers)) {
            throw new CharacterNotAllowedException();
        }

        int[] arrayOfNumbers = convertStringtoArray(numbers);

        if (existNegativeNumber(arrayOfNumbers)) {
            throw new NegativeNotAllowedException(arrayOfNumbers);
        }

        return sum(arrayOfNumbers);
    }

    private int[] convertStringtoArray(String numbers) {
        DelimiterFormat delimiterFormat = findDelimiterFormat(numbers);
        String delimiter = delimiterOf(numbers, delimiterFormat);
        numbers = removeDelimiterDefinition(numbers, delimiterFormat);

        String[] stringOfNumbers = numbers.split("[" + delimiter + "||\n]");
        return stream(stringOfNumbers).filter(s -> !s.isEmpty()).mapToInt(Integer::parseInt).toArray();
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



    private String delimiterOf(String numbers, DelimiterFormat delimiterFormat) {
        switch (delimiterFormat) {
            case None:
                return DEFAULT_DELIMITER;
            case One_Contract:
                return numbers.substring(1, 2);
            case One_Extended:
                return numbers.substring(2, 3);
            case One_Repeated:
                return numbers.substring(numbers.lastIndexOf("[")+1,numbers.lastIndexOf("]"));
            case Multiple:
                return ANY_CUSTOM_DELIMITER;
            default:
                return DEFAULT_DELIMITER;
        }
    }


    private DelimiterFormat findDelimiterFormat(String numbers) {
        if (numbers.matches(REGEX_ONE_CUSTOM_DELIMITER_EXTENDED)) {
            return DelimiterFormat.One_Extended;
        } else if (numbers.matches(REGEX_ONE_CUSTOM_DELIMITER_CONTRACT)) {
            return DelimiterFormat.One_Contract;
        } else if (numbers.matches(REGEX_REPEATED_CUSTOM_DELIMITER)){
            return DelimiterFormat.One_Repeated;
        } else if (numbers.matches(REGEX_MULTIPLE_CUSTOM_DELIMITER)){
            return DelimiterFormat.Multiple;
        } else{
            return DelimiterFormat.None;
        }
    }

    private String removeDelimiterDefinition(String numbers, DelimiterFormat delimiterFormat) {
        switch (delimiterFormat) {
            case None:
                return numbers;
            case One_Contract:
                return numbers.substring(numbers.indexOf("\n")+1);
            case One_Extended:
                return numbers.substring(numbers.indexOf("\n")+1);
            case One_Repeated:
                return numbers.substring(numbers.indexOf("]")+3);
            case Multiple:
                return numbers.substring(numbers.lastIndexOf("]")+3);
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
