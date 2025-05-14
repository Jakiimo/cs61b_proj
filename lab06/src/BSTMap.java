import java.security.Key;
import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;
    private int size;

    private class Node {
        private K key;
        private V value;
        private Node left, right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = right = null;
        }
    }

    @Override
    public void put(K key, V value) {
        Node newNode = new Node(key, value);
        root = putRec(root, newNode, key, value);
    }

    private Node putRec(Node curr, Node newNode, K key, V value) {
        if (curr == null) {
            curr = newNode;
            size++;
        }
        int cmp = key.compareTo(curr.key);
        if (cmp < 0) {
            curr.left = putRec(curr.left, newNode, key, value);
        } else if (cmp > 0) {
            curr.right = putRec(curr.right, newNode, key, value);
        } else if (cmp == 0) {
            //更新键值
            curr.value = value;
        }
        return curr;
    }

    @Override
    public V get(K key) {
        if (getRec(root, key) == null) return null;
        return getRec(root, key).value;
    }

    private Node getRec(Node curr, K key) {

        if (curr == null) {
            return null;
        }
        int cmp = key.compareTo(curr.key);
        if (cmp == 0) {
            return curr;
        } else if (cmp < 0) {
            return getRec(curr.left, key);
        } else if (cmp > 0) {
            return getRec(curr.right, key);
        } else {
            return curr;
        }
    }


    @Override
    public boolean containsKey(K key) {
        if (getRec(root, key) == null) return false;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new TreeSet<>();
        collectKeyInOrder(root, keySet);
        return keySet;
    }

    private void collectKeyInOrder(Node node, Set<K> keySet) {
        if (node == null) return;
        collectKeyInOrder(node.left, keySet);
        keySet.add(node.key);
        collectKeyInOrder(node.right, keySet);
    }

    //这里GPT了 我找不出被删除节点的父母节点
    @Override
    public V remove(K key) {
        Removed result = removeRec(root, key);
        root = result.node;
        if (result.removedValue != null) size--;
        return result.removedValue;
    }

    // 帮助类：返回新子树和被删除的值
    private class Removed {
        Node node;
        V removedValue;

        Removed(Node node, V val) {
            this.node = node;
            this.removedValue = val;
        }
    }

    private Removed removeRec(Node curr, K key) {
        if (curr == null) return new Removed(null, null);

        int cmp = key.compareTo(curr.key);
        if (cmp < 0) {
            Removed result = removeRec(curr.left, key);
            curr.left = result.node; // 把删后的子树重新挂上去
            return new Removed(curr, result.removedValue);
        } else if (cmp > 0) {
            Removed result = removeRec(curr.right, key);
            curr.right = result.node;
            return new Removed(curr, result.removedValue);
        } else {
            // 找到了要删除的节点
            V oldValue = curr.value;
            if (curr.left == null) return new Removed(curr.right, oldValue);
            if (curr.right == null) return new Removed(curr.left, oldValue);

            // 有两个子节点：找右子树最小节点
            Node successor = findMin(curr.right);
            successor.right = deleteMin(curr.right);
            successor.left = curr.left;
            return new Removed(successor, oldValue);
        }
    }

    // 找右子树最小节点
    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // 删除最小节点（用于清理 successor）
    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        return node;
    }

/*    @Override
    public V remove(K key) {
        //被删除结点只有一个子节点，则则节点代替当前节点
        //被删除结点有两个子节点，则找出右孩子的最左结点
        Node node = getRec(root, key);//要删除的节点
        removeRec(node, key);
        return null;
    }

    //curr为要删除的结点
    private void removeRec(Node curr, K key) {
        if (curr == null) return;
        if (curr.left == null && curr.right == null) {
            curr = null;
        } else if ((curr.left == null && curr.right != null)) {  //只有右结点
            curr = curr.right;
        } else if (curr.left != null && curr.right == null) { //只有左节点
            curr = curr.left;
        } else {//两边都有
            curr = getLeft(curr);
        }
    }

    public Node getLeft(Node curr) {//递归找到最左侧结点
        if (curr.left == null) return curr;
        else {
            curr = curr.left;
            getLeft(curr);
        }
        return curr;
    }*/

    @Override
    public Iterator<K> iterator() {
        List<K> keys = new ArrayList<>();
        inOrderTraversal(root, keys);
        return keys.iterator();
    }

    private void inOrderTraversal(Node node, List<K> keys) {
        if (node == null) return;
        inOrderTraversal(node.left, keys);
        keys.add(node.key);
        inOrderTraversal(node.right, keys);
    }


    public BSTMap() {
        root = null;
        size = 0;
    }


}
