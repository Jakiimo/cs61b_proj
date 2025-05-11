import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    public void testAddFirst() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        //assertThat(deque.toList()).containsExactly(1);
        deque.addFirst(2);
        //assertThat(deque.toList()).containsExactly(2, 1).inOrder();
        deque.addFirst(3);
        //assertThat(deque.toList()).containsExactly(3, 2, 1).inOrder();
        deque.addFirst(4);
        deque.addFirst(5);
        assertThat(deque.toList()).containsExactly(5, 4, 3, 2, 1).inOrder();
    }

    @Test
    public void testAddLast() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        assertThat(deque.toList()).containsExactly(1);
        deque.addLast(2);
        assertThat(deque.toList()).containsExactly(1, 2).inOrder();
        deque.addLast(3);
        assertThat(deque.toList()).containsExactly(1, 2, 3).inOrder();
        deque.addLast(4);
        assertThat(deque.toList()).containsExactly(1, 2, 3, 4).inOrder();
        deque.addLast(5);
        assertThat(deque.toList()).containsExactly(1, 2, 3, 4, 5).inOrder();
    }

    @Test
    public void testIsEmpty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        assertThat(deque.isEmpty()).isTrue();
        deque.addLast(1);
        assertThat(deque.isEmpty()).isFalse();
    }

    @Test
    public void testSize() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void testRemoveFirst() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.removeFirst();
        System.out.println(deque.size());
        assertThat(deque.toList()).containsExactly(2, 3);
    }

    @Test
    public void testRemoveLast() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.removeLast();
        System.out.println(deque.size());
        assertThat(deque.toList()).containsExactly(1, 2);
    }

    @Test
    public void testGet() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        assertThat(deque.get(0)).isEqualTo(1);
        assertThat(deque.get(1)).isEqualTo(2);
    }
}
