import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by annamartignano on 07/06/17.
 */
public class StringCalculatorTest {
    private StringCalculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new StringCalculator();
    }

    @Test
    public void stringa_vuota_ritorna_0() throws CaratteriNonAmmessiException {
        assertEquals(0, (int) calculator.add(""));
    }

    @Test
    public void solo_un_numero_ritorna_numero() throws CaratteriNonAmmessiException {
        assertEquals(1, (int) calculator.add("1"));
    }

    @Test
    public void più_numeri_ritorna_la_somma() throws CaratteriNonAmmessiException {
        assertEquals(3, (int) calculator.add("1,2"));
    }

    @Test
    public void funziona_anche_con_caratteri_speciali() throws CaratteriNonAmmessiException {
        assertEquals(6, (int) calculator.add("1,2\n3"));
    }

    @Test(expected = CaratteriNonAmmessiException.class)
    public void questa_stringa_non_ci_piace() throws CaratteriNonAmmessiException {
        calculator.add("1,\n");
        //catturare le eccezioni senza usare il catch()
    }

    @Test
    public void accetta_delimitatori_customizzati() throws Exception {
        assertEquals(3, (int) calculator.add("//;\n1;2"));
    }
}

