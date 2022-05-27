package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
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

    private void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];
        int newNextFirst = temp.length / 2 - size / 2 - 1;
        int newNextLast = temp.length / 2 - size / 2;
        for (int i = 0; i < size; i += 1) {
            T item = this.get(i);
            temp[newNextLast] = item;
            newNextLast += 1;
        }
        items = temp;
        nextFirst = newNextFirst;
        nextLast = newNextLast;
    }

    @Override
    public void addFirst(T item) {
        if (nextFirst == nextLast) {
            resize(items.length * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (nextFirst == nextLast) {
            resize(items.length * 2);
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
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

    @Override
    public T removeFirst() {
        if (size > 0) {
            T first = items[plusOne(nextFirst)];
            items[plusOne(nextFirst)] = null;
            nextFirst = plusOne(nextFirst);
            size -= 1;
            if ((size < items.length / 4) && (size > 16)) {
                resize(items.length / 4 + 2);
            }
            return first;
        }
        return null;
    }

    @Override
    public T removeLast() {
        if (size > 0) {
            T last = items[minusOne(nextLast)];
            items[minusOne(nextLast)] = null;
            nextLast = minusOne(nextLast);
            size -= 1;
            if ((size < items.length / 4) && (size > 16)) {
                resize(items.length / 4 + 2);
            }
            return last;
        }
        return null;
    }

    @Override
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

    private class ArrayIterator implements Iterator<T> {
        int p;

        ArrayIterator() {
            p = plusOne(nextFirst);
        }
        @Override
        public boolean hasNext() {
            return p != nextLast;
        }

        @Override
        public T next() {
            T value = items[p];
            p = plusOne(p);
            return value;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Deque)) {
            return false;
        }
        Deque<T> o = (Deque<T>) other;
        if (this.size() != o.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i += 1) {
            if (this.get(i) != o.get(i)) {
                return false;
            }
        }
        return true;
    }
}
