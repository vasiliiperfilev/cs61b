package randomizedtest;
import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import spark.utils.Assert;

public class ArrayTest {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> lst1 = new AListNoResizing<>();
        BuggyAList<Integer> lst2 = new BuggyAList<>();
        lst1.addLast(4);
        lst2.addLast(4);
        lst1.addLast(5);
        lst2.addLast(5);
        lst1.addLast(6);
        lst2.addLast(6);
        Assert.isTrue(lst1.removeLast() == lst2.removeLast(), "Mes");
        Assert.isTrue(lst1.removeLast() == lst2.removeLast(), "Mes");
        Assert.isTrue(lst1.removeLast() == lst2.removeLast(), "Mes");
    }

    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> Lbuggy = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                Lbuggy.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int buggySize = Lbuggy.size();
            } else if (operationNumber == 2 && L.size() > 0) {
                L.removeLast();
                L.getLast();
                Lbuggy.getLast();
                Lbuggy.removeLast();
            }
        }
    }
}
