import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<Object> {
    private static int CAPACITY = 4;
    private T[] items;
    private int size;
    private int First;
    private int nextLast;

    public ArrayDeque61B() {
        items = (T[]) new Object[CAPACITY];
        size = 0;
        First = 0;
        nextLast = 0;
    }

    @Override
    public void addFirst(Object x) {
        int pos = Math.floorMod(First - 1, CAPACITY);
        //First = Math.floorMod(First - 1, CAPACITY);
        if (items[pos] == null) {
            items[pos] = (T) x;
            First = pos;
            size++;
        }
    }

    @Override
    public void addLast(Object x) {
        if (items[nextLast] == null) {
            items[nextLast] = (T) x;
            nextLast = (nextLast + 1) % CAPACITY;
            size++;
        }

    }

    @Override
    public List<Object> toList() {
        List<T> returnList = new ArrayList<>();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                if (items != null) returnList.add(items[Math.floorMod(First + i, CAPACITY)]);
            }
            return (List<Object>) returnList;
        } else {
            return null;
        }

    }

    @Override
    public boolean isEmpty() {
        if (size == 0) return true;
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object removeFirst() {
        if (isEmpty() == false) {
            items[First] = null;
            First = (First + 1) % CAPACITY;
            size--;
            return items[First];
        }
        return null;
    }

    @Override
    public Object removeLast() {
        if (isEmpty() == false) {
            items[Math.floorMod(nextLast - 1, CAPACITY)] = null;
            nextLast = (nextLast - 1) % CAPACITY;
            size--;
        }
        return null;
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index > CAPACITY - 1) return null;
        return items[index];
    }

    @Override
    public Object getRecursive(int index) {
        return null;
    }
}
