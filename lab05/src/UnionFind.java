public class UnionFind {
    // TODO: Instance variables
    private int[] parent;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        if (N < 0) throw new IllegalArgumentException("N must be non-negative");
        parent = new int[N];
        // 初始时，每个元素是一个集合的根，大小为 1（用 -1 表示）
        for (int i = 0; i < N; i++) {
            parent[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        //只要爬到根部就能获得集合大小
        if (v < 0 || v >= parent.length) {
            throw new IllegalArgumentException("v must be in range [0,N-1]");
        }
        return Math.abs(parent[find(v)]);
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    //数组索引才表示元素
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        return parent[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        //检测两元素是否有相同的根
        int temp1 = find(v1);
        int temp2 = find(v2);
        if (temp1 == temp2) return true;
        return false;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        //未路径压缩
/*        if (v < 0 || v >= parent.length) {
            throw new IllegalArgumentException("v must be in range [0,N-1]");
        } else {
            //如果不是根
            if (!(parent[v] < 0)) {
                v = find(parent[v]);
            }
        }*/
        //处理路径压缩
        int temp = v;//temp为当前子节点
        if (v < 0 || v >= parent.length) {
            throw new IllegalArgumentException("v must be in range [0,N-1]");
        } else {
            //如果不是根，递归find()直到查找到根
            //如果下面if被执行了，也就是递归了，说明当前元素temp有根，应该将temp指向递归出口处的v（根
            if (!(parent[v] < 0)) {
                v = find(parent[v]);
                parent[temp] = v;//路径压缩，更新当前节点temp的父节点
            }
        }
        //此时的v经过递归已经成为根部的元素
        //问题出在当这个集合只有自己时依旧给自己赋了根部的值，当这个元素为根时应该不执行parent[temp] = v;
        //应该调整为只有第一次执行find时该集合就只有一个元素时，不执行该指令   find()返回的是当前集合的根
        //路径压缩应该发生在路径回溯上  因为无法通过根来找到未被压缩的路径
        //if (parent[temp] < 0 && temp != v) parent[temp] = v;

        return v;//返回根
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        int sizeofv1 = sizeOf(v1);
        int sizeofv2 = sizeOf(v2);
        if (v1 < 0 || v1 >= parent.length || v2 < 0 || v2 >= parent.length) {
            throw new IllegalArgumentException("v must be in range [0,N-1]");
        } else if (connected(v1, v2)) {//判断两元素是否在同一集合
            return;
        } else {
            if (sizeOf(v1) <= sizeOf(v2)) {//两集合大小相等或v1<v2
                parent[find(v1)] = find(v2);//将v1树根接到v2树根上
                find(v1);//路径压缩
                parent[find(v2)] = -(sizeofv1 + sizeofv2);//确定了新树根元素为find(v2)/更新树根显示的集合大小
            } else if (sizeOf(v1) > sizeOf(v2)) {
                parent[find(v2)] = find(v1);//将v2树根接到v1树根上
                find(v2);
                parent[find(v1)] = -(sizeofv1 + sizeofv2);
            }
        }
    }
}


