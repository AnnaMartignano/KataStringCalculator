import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.*;
import static java.util.Arrays.*;

/**
 * Created by annamartignano on 07/06/17.
 */
public class StringCalculator {


    public Integer add(String numbers) throws CaratteriNonAmmessiException {
        if (numbers.isEmpty()) {
            return 0;

        } else if (existInvalidChars(numbers)){
            throw new CaratteriNonAmmessiException();

        } else {
            //caso generico, eslude tutti i caratteri che non sono "digit" [^0-9]
            return stream(numbersFor(numbers))
                    .mapToInt(Integer::parseInt)
                    .sum();
        }
    }

    private boolean existInvalidChars(String numbers) {
        return numbers.contains(",\n")||numbers.contains("\n,");
    }

    private String[] numbersFor(String numbers) {
        return numbers.split("[,||\n]");
    }
}
