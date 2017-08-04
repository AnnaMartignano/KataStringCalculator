/**
 * Created by annamartignano on 05/07/17.
 */
public class NegativeNotAllowedException extends Exception {
    NegativeNotAllowedException(int[] myArray) {
        for (int i = 0; i < myArray.length; i++) {
            if (myArray[i] < 0) {
                System.out.print(myArray[i]);
            }
        }
    }
}
