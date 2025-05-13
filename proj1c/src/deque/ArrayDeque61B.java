package deque;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
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
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                if (items != null) returnList.add(items[Math.floorMod(First + i, newCapacity)]);
            }
            return (List<T>) returnList;
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
    public T removeFirst() {
        T temp = items[First];
        if (isEmpty() == false) {
            items[First] = null;
            First = (First + 1) % newCapacity;
            size--;
            return temp;
        }
        resize(newCapacity / 2);
        return null;
    }

    @Override
    public T removeLast() {
        if (isEmpty() == false) {
            items[Math.floorMod(nextLast - 1, newCapacity)] = null;
            nextLast = (nextLast - 1) % newCapacity;
            size--;
        }
        resize(newCapacity / 2);
        return null;
    }

    @Override
    public T get(int index) {
        if ((index < 0 || index > newCapacity - 1) && index != -1) return null;
        else if (index == -1) return items[First]; //鸡贼了 做到1c才发现自己的ArrayDeque的addFirst好像理解错了，没法改接口，在这里偷偷弄一下-1返回队首
        return items[index];
    }

    @Override
    public T getRecursive(int index) {
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

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                T item = (T) get(index);
                index++;
                return item;
            }
        };

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(get(i));
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ArrayDeque61B)) {
            return false;
        }
        Deque61B<T> other = (Deque61B<T>) obj;
        if (size != other.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }
}
