package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {

    private class Node {
        T value;
        Node next;
        Node previous;

        Node(T value, Node next, Node previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.previous = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public LinkedListDeque(T x) {
        sentinel = new Node(null, null, null);
        Node newNode = new Node(x, sentinel, sentinel);
        sentinel.next = newNode;
        sentinel.previous = newNode;
        size = 1;
    }

    @Override
    public void addFirst(T item) {
        sentinel.next = new Node(item, sentinel.next, sentinel);
        size += 1;
    }

    @Override
    public void addLast(T item) {
        Node newLast = new Node(item, sentinel, sentinel.previous);
        sentinel.previous.next = newLast;
        sentinel.previous = newLast;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node head = this.sentinel.next;
        while (head != sentinel) {
            System.out.print(head.value + " ");
            head = head.next;
        }
        System.out.println("");
    }

    @Override
    public T removeFirst() {
        if (size > 0) {
            Node first = sentinel.next;
            sentinel.next = sentinel.next.next;
            sentinel.next.previous = sentinel;
            size -= 1;
            return first.value;
        }
        return null;
    }

    @Override
    public T removeLast() {
        if (size > 0) {
            Node last = sentinel.previous;
            sentinel.previous = sentinel.previous.previous;
            sentinel.previous.next = sentinel;
            size -= 1;
            return last.value;
        }
        return null;
    }

    @Override
    public T get(int index) {
        if (index < size) {
            Node head = sentinel.next;
            while (index != 0) {
                head = head.next;
                index -= 1;
            }
            return head.value;
        }
        return null;
    }

    private T getRecursiveHelper(int index, Node head) {
        if (index == 0) {
            return head.value;
        }
        return getRecursiveHelper(index - 1, head.next);
    }

    public T getRecursive(int index) {
        if (index < size) {
            getRecursiveHelper(index, sentinel.next);
        }
        return null;
    }

    private class LinkedListIterator implements Iterator<T> {
        Node head;

        LinkedListIterator() {
            head = sentinel.next;
        }
        @Override
        public boolean hasNext() {
            return head != sentinel;
        }

        @Override
        public T next() {
            T value = head.value;
            head = head.next;
            return value;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        LinkedListDeque<T> o = (LinkedListDeque<T>) other;
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
