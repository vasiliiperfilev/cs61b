package deque;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import edu.princeton.cs.algs4.StdRandom;

public class MaxArrayDequeTest {

    @Test
    /** Tests maxInt
     *  It returns max determined by comparator function
     *  if more than one max exists returns first*/
    public void addIsEmptySizeTest() {
        MaxArrayDeque<Integer> ad = new MaxArrayDeque<>(MaxArrayDeque.getIntComparator());

        ad.addFirst(2);
        ad.addFirst(4);
        ad.addFirst(3);
        ad.addFirst(1);

        assertEquals(4, ad.max(), 0.0);
        assertEquals(1, ad.max(MaxArrayDeque.getReminderBy2Comparator()), 0.0);
    }
}
