package deque;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import edu.princeton.cs.algs4.StdRandom;

public class ArrayDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        ArrayDeque<String> ad = new ArrayDeque<String>();

        assertTrue("A newly initialized ArrayDeque should be empty", ad.isEmpty());
        ad.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, ad.size());
        assertFalse("ad should now contain 1 item", ad.isEmpty());

        ad.addLast("middle");
        assertEquals(2, ad.size());

        ad.addLast("back");
        assertEquals(3, ad.size());

        System.out.println("Printing out deque: ");
        ad.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        // should be empty
        assertTrue("ad should be empty upon initialization", ad.isEmpty());

        ad.addFirst(10);
        // should not be empty
        assertFalse("ad should contain 1 item", ad.isEmpty());

        ad.removeFirst();
        // should be empty
        assertTrue("ad should be empty after removal", ad.isEmpty());

        ad.addFirst(7);
        assertEquals(ad.get(0), 7, 0.0);
        // should not be empty
        assertFalse("ad should contain 1 item", ad.isEmpty());

        ad.removeFirst();
        // should be empty
        assertTrue("ad should be empty after removal", ad.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(3);

        ad.removeLast();
        ad.removeFirst();
        ad.removeLast();
        ad.removeFirst();

        int size = ad.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create ArrayDeques with different parameterized types*/
    public void multipleParamTest() {
        ArrayDeque<String>  ad1 = new ArrayDeque<String>();
        ArrayDeque<Double>  ad2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> ad3 = new ArrayDeque<Boolean>();

        ad1.addFirst("string");
        ad2.addFirst(3.14159);
        ad3.addFirst(true);

        String s = ad1.removeFirst();
        double d = ad2.removeFirst();
        boolean b = ad3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, ad.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, ad.removeLast());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            ad.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) ad.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) ad.removeLast(), 0.0);
        }
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void fillUpEmptyFillUpAgain() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        // fill
        for (int i = 0; i < 9; i += 1) {
            ad1.addFirst(i);
        }
        // empty
        for (int i = 8; i >= 0; i -= 1) {
            assertEquals("Should have the same value", i, ad1.removeFirst(), 0.0);
        }
        assertEquals("Should be empty", 0, ad1.size(), 0.0);
        // fill again
        for (int i = 3; i < 80; i++) {
            ad1.addLast(i);
        }
        // empty again
        for (int i = 3; i < 80; i++) {
            assertEquals("Should have the same value", i, ad1.removeFirst(), 0.0);
        }
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void iterationTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 10; i++) {
            lld1.addLast(i);
        }

        for (int i : lld1) {
            System.out.println(i);
        }
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void equalsTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 10; i += 1) {
            lld1.addLast(i);
        }

        ArrayDeque<Integer> lld2 = new ArrayDeque<Integer>();
        for (int i = 0; i < 10; i += 1) {
            lld2.addLast(i);
        }

        assertEquals(lld1, lld2);
    }
}
