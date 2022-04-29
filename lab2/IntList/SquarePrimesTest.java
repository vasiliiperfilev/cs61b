package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesOnly1Elem() {
        // prime
        IntList lst = IntList.of(17);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("289", lst.toString());
        assertTrue(changed);
        // not prime
        IntList lst2 = IntList.of(4);
        boolean changed2 = IntListExercises.squarePrimes(lst);
        assertEquals("4", lst2.toString());
        assertTrue(!changed2);
    }

    @Test
    public void testSquarePrimesWith0() {
        // prime
        IntList lst = IntList.of(0);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("0", lst.toString());
        assertTrue(!changed);
    }

    @Test
    public void testSquareAllPrimes() {
        // prime
        IntList lst = IntList.of(17, 5, 3);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("289 -> 25 -> 9", lst.toString());
        assertTrue(changed);
    }
}
