package deque;

public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextLast;
    private int nextFirst;

    /** Creates an empty list. */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
        size = 0;
    }

    private int minusOne(int index) {
        if (index - 1 < 0) {
            return items.length - 1;
        }
        return index - 1;
    }

    private int plusOne(int index) {
        if (index + 1 > items.length - 1) {
            return 0;
        }
        return index + 1;
    }

    public void addFirst(T item) {
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    public void addLast(T item) {
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (nextLast > nextFirst && size < items.length) {
            for (int i = nextFirst + 1; i < nextLast; i += 1) {
                System.out.print(items[i] + " ");
            }
        } else {
            for (int i = nextFirst + 1; i < items.length; i += 1) {
                System.out.print(items[i] + " ");
            }
            for (int i = 0; i < nextLast; i++) {
                System.out.print(items[i] + " ");
            }
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size > 0) {
            T first = items[plusOne(nextFirst)];
            items[plusOne(nextFirst)] = null;
            nextFirst = plusOne(nextFirst);
            size -= 1;
            return first;
        }
        return null;
    }

    public T removeLast() {
        if (size > 0) {
            T last = items[nextLast - 1];
            items[nextLast - 1] = null;
            nextLast = minusOne(nextLast);
            size -= 1;
            return last;
        }
        return null;
    }

    public T get(int index) {
        if (index < size) {
            if (nextFirst + 1 + index > items.length - 1) {
                return items[index - (items.length - nextFirst - 1)];
            } else {
                return items[nextFirst + 1 + index];
            }
        }
        return null;
    }
}
