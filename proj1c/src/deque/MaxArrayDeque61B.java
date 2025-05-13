package deque;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class MaxArrayDeque61B<T> implements Deque61B<T> {
    private ArrayDeque61B<T> data;
    private Comparator<T> defaultComparator;

    public MaxArrayDeque61B(Comparator<T> c) {
        data = new ArrayDeque61B<>();
        defaultComparator = c;
    }

    @Override
    public void addFirst(T item) {
        data.addFirst(item);
    }

    @Override
    public void addLast(T item) {
        data.addLast(item);
    }

    @Override
    public List<T> toList() {
        return List.of();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public T removeFirst() {
        return (T) data.removeFirst();
    }

    @Override
    public T removeLast() {
        return (T) data.removeLast();
    }

    @Override
    public T get(int index) {
        return (T) data.get(index);
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public Iterator<T> iterator() {
        return (Iterator<T>) data.iterator();
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public boolean equals(Object o) {
        return data.equals(o);
    }

    public T max() {
        return max(defaultComparator);
    }

    public T max(Comparator<T> c) {
        T maxItem = null;

        for (int i = 0; i < data.size(); i++) {
            T current = (T) data.get(i);
            if (current == null) continue;

            if (maxItem == null || c.compare(current, maxItem) > 0) {
                maxItem = current;
            }
        }

        return maxItem;
    }
}