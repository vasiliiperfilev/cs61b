package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
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
}
