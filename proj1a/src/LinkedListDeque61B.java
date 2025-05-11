import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private Node sentinel;
    private int size;

    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public class Node {
        T item;
        Node next;
        Node prev;

        Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }


    @Override
    public void addFirst(T x) {
        Node newNode = new Node(x, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode; //第一个结点 s.prev=1  插两个节点,s.prev=1
        size++;
    }

    @Override
    public void addLast(T x) {
        Node newNode = new Node(x, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        Node curr = sentinel.next;
        while (curr != sentinel) {
            list.add(curr.item);
            curr = curr.next;
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        if (sentinel.next == sentinel) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        Node temp = sentinel.next;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        return temp.item;
    }

    @Override
    public T removeLast() {
        Node temp = sentinel.prev;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        return temp.item;
    }

    @Override
    public T get(int index) {
        Node curr = sentinel;  //sentinel.index=0
        if (index < 0 || index > size) {
            return null;
        } else {
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
        }
        return (T) curr.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(sentinel, index);  // 从 sentinel 开始
    }

    private T getRecursiveHelper(Node node, int index) {
        if (index == 0) {
            return (T) node.item;
        }
        return (T) getRecursiveHelper(node.next, index - 1);
    }

}
