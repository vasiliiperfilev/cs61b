package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    MaxArrayDeque(Comparator<T> c) {
        super();
        this.comparator = c;
    }

    public T max() {
        if (size() > 0) {
            int p = 0;
            T curMax = get(0);
            for (int i = 0; i < size() - 1; i++) {
                p += 1;
                T curVal = get(p);
                if (comparator.compare(curMax, curVal) < 0) {
                    curMax = curVal;
                }
            }
            return curMax;
        }
        return null;
    }

    public T max(Comparator<T> c) {
        Comparator<T> temp = this.comparator;
        this.comparator = c;
        T max = max();
        this.comparator = temp;
        return max;
    }

    private static class IntComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer num1, Integer num2) {
            return num1 - num2;
        }
    }

    public static Comparator<Integer> getIntComparator() {
        return new IntComparator();
    }

    private static class ReminderBy2Comparator implements Comparator<Integer> {

        @Override
        public int compare(Integer num1, Integer num2) {
            return num1 % 2 - num2 % 2;
        }
    }

    public static Comparator<Integer> getReminderBy2Comparator() {
        return new ReminderBy2Comparator();
    }
}
