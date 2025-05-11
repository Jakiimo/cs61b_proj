import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<Object> {
    private static int CAPACITY = 4;
    private T[] items;
    private int size;
    private int First;
    private int nextLast;
    private int newCapacity;
    private double rate;

    public ArrayDeque61B() {
        items = (T[]) new Object[CAPACITY];
        size = 0;
        First = 0;
        nextLast = 0;
        newCapacity = items.length;
        rate = items.length / newCapacity;
    }

    @Override
    public void addFirst(Object x) {
        int pos = Math.floorMod(First - 1, newCapacity);
        if (items[pos] == null) {
            items[pos] = (T) x;
            First = pos;
            size++;
        } else {
            resize(newCapacity * 2);
            addFirst(x);
        }
    }

    @Override
    public void addLast(Object x) {
        if (items[nextLast] == null) {
            items[nextLast] = (T) x;
            nextLast = (nextLast + 1) % newCapacity;
            size++;
        } else {
            nextLast = Math.floorMod(nextLast - 1, newCapacity);
            resize(newCapacity * 2);
            nextLast = Math.floorMod(nextLast + 1, newCapacity);
            addLast(x);
        }

    }

    @Override
    public List<Object> toList() {
        List<T> returnList = new ArrayList<>();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                if (items != null) returnList.add(items[Math.floorMod(First + i, newCapacity)]);
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
        resize(newCapacity / 2);
        return null;
    }

    @Override
    public Object removeLast() {
        if (isEmpty() == false) {
            items[Math.floorMod(nextLast - 1, CAPACITY)] = null;
            nextLast = (nextLast - 1) % CAPACITY;
            size--;
        }
        resize(newCapacity / 2);
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

    public boolean resize(int Capacity) {
        if (isEmpty() == false) {
            Capacity = newCapacity * 2;
            T[] newItems = (T[]) new Object[Capacity];
            for (int i = 0; i < size; i++) {
                newItems[i] = items[Math.floorMod(First + i, Capacity)];
            }
            items = newItems;
            newCapacity = Capacity;
            return true;
        }
        if (rate < 0.25 && Capacity > CAPACITY) {
            Capacity = newCapacity / 2;
            T[] newItems1 = (T[]) new Object[Capacity];
            for (int i = 0; i < size; i++) {
                newItems1[i] = items[Math.floorMod(First + i, Capacity)];
            }
            items = newItems1;
            newCapacity = Capacity;
            return true;
        }
        return false;
    }
}
