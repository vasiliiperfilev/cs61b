package deque;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;

public class MaxArrayDequeTest {

    private static class ReminderBy2Comparator implements Comparator<Integer> {

        @Override
        public int compare(Integer num1, Integer num2) {
            return num1 % 2 - num2 % 2;
        }
    }

    private static class IntComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer num1, Integer num2) {
            return num1 - num2;
        }
    }

    @Test
    /** Tests maxInt
     *  It returns max determined by comparator function
     *  if more than one max exists returns first*/
    public void addIsEmptySizeTest() {
        MaxArrayDeque<Integer> ad = new MaxArrayDeque<>(new IntComparator());

        ad.addFirst(2);
        ad.addFirst(4);
        ad.addFirst(3);
        ad.addFirst(1);

        assertEquals(4, ad.max(), 0.0);
        assertEquals(1, ad.max(new ReminderBy2Comparator()), 0.0);
    }
}
