import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class TestBST {
    @Test
    public void testPut() {
        BSTMap<Integer, String> map = new BSTMap<>();
        map.put(8, "a");
        map.put(4, "b");
        map.put(6, "e");
        map.put(5, "c");
        map.put(7, "p");
        map.put(2, "d");
        map.put(1, "==");

        System.out.println(map.get(4));
        System.out.println(map.containsKey(4));
        System.out.println(map.containsKey(9999));
        System.out.println(map.size());

        map.remove(4);

        map.clear();
    }

}
