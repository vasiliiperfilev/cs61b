package bstmap;

import edu.princeton.cs.algs4.BST;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V extends Comparable<V>> implements Map61B<K, V> {
    BSTNode root;

    private class BSTNode {
        BSTNode left;
        BSTNode right;
        K key;
        V value;
        int size;

        public BSTNode(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    public BSTMap() {
    }
    @Override
    public void clear() {
        root = null;
    }

    private BSTNode find(BSTNode t, K key) {
        if (t == null) {
            return null;
        }
        if (t.key.equals(key)) {
            return t;
        } else if (t.key.compareTo(key) > 0) {
            return find(t.left, key);
        } else {
            return find(t.right, key);
        }
    }

    @Override
    public boolean containsKey(K key) {
        return null != find(this.root, key);
    }

    @Override
    public V get(K key) {
        V value;
        BSTNode node = find(this.root, key);
        if (node != null) {
            value = node.value;
        } else {
            value = null;
        }
        return value;
    }

    @Override
    public int size() {
        if (root != null) {
            return root.size;
        }
        return 0;
    }

    public BSTNode put (BSTNode t, K key, V value) {
        if (t == null) {
            return new BSTNode(key, value, 1);
        }
        if (t.key.compareTo(key) > 0) {
            t.left = put(t.left, key, value);
        } else {
            t.right = put(t.right, key, value);
        }
        t.size += 1;
        return t;
    }

    @Override
    public void put(K key, V value) {
        if (find(root, key) == null) {
            root = put(root, key, value);
        }
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    private BSTNode findMax(BSTNode node) {
        BSTNode max = node;
        if (max == null) {
            return null;
        }
        while (max.right != null) {
            max = max.right;
        }
        return max;
    }

    private BSTNode findParent(BSTNode start, BSTNode node) {
        if (start == null || start.key.equals(node.key)) {
            return null;
        }
        if (start.left != null && start.left.equals(node)) {
            return start;
        }
        if (start.right != null && start.right.equals(node)) {
            return start;
        }
        if (start.key.compareTo(node.key) > 0) {
            return findParent(start.left, node);
        } else {
            return findParent(start.right, node);
        }
    }

    private BSTNode detachFromParent(BSTNode node) {
        BSTNode parent = findParent(root, node);
        if (parent != null) {
            if (parent.right!= null && parent.right.key.equals(node.key)) {
                parent.right = null;
            } else {
                parent.left = null;
            }
            parent.size -= node.size;
            return parent;
        }
        return null;
    }

    private BSTNode put(BSTNode t, BSTNode node) {
        if (t == null) {
            return node;
        }
        if (t.key.compareTo(node.key) > 0) {
            t.left = put(t.left, node);
        } else {
            t.right = put(t.right, node);
        }
        t.size += node.size;
        return t;
    }

    @Override
    public V remove(K key) {
        // find node by key, return null if it's null
        BSTNode deleteNode = find(root, key);
        if (deleteNode == null) {
            return null;
        }
        V deleteValue = deleteNode.value;
        // find the biggest node at the left of node being deleted
        BSTNode leftMax = findMax(deleteNode.left);
        if (leftMax != null) {
            // get left child of the left biggest node
            BSTNode temp = leftMax.left;
            detachFromParent(leftMax);
            // replace the node being deleted with the biggest node at the left
            deleteNode.key = leftMax.key;
            deleteNode.value = leftMax.value;
            // put back into the new tree left child of the ex biggest left node
            if (temp != null) {
                put(temp.key, temp.value);
            }
        } else {
            BSTNode deleteParent = detachFromParent(deleteNode);
            if (deleteNode.equals(root)) {
                root = null;
            }
            // reattach delete node children
            if (deleteNode.right != null) {
                deleteParent = put(deleteParent, deleteNode.right);
            }
        }
        return deleteValue;
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
