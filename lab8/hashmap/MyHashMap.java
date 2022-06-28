package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    double loadFactor;
    int size;
    HashSet<K> keySet;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        buckets = new Collection[16];
        loadFactor = 0.75;
        keySet = new HashSet<>();
        size = 0;
    }

    public MyHashMap(int initialSize) {
        buckets = new Collection[initialSize];
        loadFactor = 0.75;
        size = 0;
        keySet = new HashSet<>();
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = new Collection[initialSize];
        loadFactor = maxLoad;
        size = 0;
        keySet = new HashSet<>();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList<Node>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void clear() {
        buckets = new Collection[16];
        size = 0;
        keySet = new HashSet<>();
    }

    private int getBucketIndex(K key) {
        int index = (key.hashCode() & 0x7fffffff) % buckets.length;
        return index;
    }

    @Override
    public boolean containsKey(K key) {
        Collection<Node> nodeList = buckets[getBucketIndex(key)];
        if (nodeList != null) {
            Iterator<Node> nodeListIterator = nodeList.iterator();
            while (nodeListIterator.hasNext()) {
                Node next = nodeListIterator.next();
                if (next.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        Collection<Node> bucket = buckets[getBucketIndex(key)];
        if (bucket != null) {
            Iterator<Node> iterator = buckets[getBucketIndex(key)].iterator();
            while (iterator.hasNext()) {
                Node next = iterator.next();
                if (next.key.equals(key)) {
                    return next.value;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (keySet.contains(key)) {
            replace(key, value);
        } else {
            Node newNode = createNode(key, value);
            int bucketIndex = getBucketIndex(key);
            if (buckets[bucketIndex] == null) {
                buckets[bucketIndex] = createBucket();
            }
            buckets[bucketIndex].add(newNode);
            keySet.add(key);
            size += 1;
            if (size * 1.0 / buckets.length > loadFactor) {
                resize((int) Math.round(size * 2));
            }
        }
    }

    private void replace(K key, V value) {
        Collection<Node> bucket = buckets[getBucketIndex(key)];
        if (bucket != null) {
            Iterator<Node> iterator = buckets[getBucketIndex(key)].iterator();
            while (iterator.hasNext()) {
                Node next = iterator.next();
                if (next.key.equals(key)) {
                    next.value = value;
                }
            }
        }
    }

    private void resize(int newSize) {
        MyHashMap<K, V> temp = new MyHashMap<>(newSize);
        for (int i = 0; i < buckets.length; i++) {
            if ( buckets[i] != null) {
                for (Node node : buckets[i]) {
                    temp.put(node.key, node.value);
                }
            }
        }
        this.buckets = temp.buckets;
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }

}
