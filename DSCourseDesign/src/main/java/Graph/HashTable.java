/**
 * 名称(String) -> 编号(idex)哈希表
 */
package Graph;

public class HashTable {
    final static int MAX = 1024;
    final static int mod = 1000000+7;

    class Node {
        int next;
        int value;
        String key;//键值为名称
        public Node() {
            next = 0;
            value = 0;
            key = new String();
        }

        public Node(int next, int value, String key) {
            this.next = next;
            this.value = value;
            this.key = key;
        }
    }
    Node[] data;
    boolean[] dataOccupy;
    int[] head;
    int cnt;
    int size;

    public HashTable() {
        data = new Node[MAX + 5];
        for(Node elem: data) elem = new Node();

        dataOccupy = new boolean[MAX + 5];
        dataOccupy[0] = true;

        head = new int[mod + 5];
        cnt = 0;
        size = 0;
    }

    public boolean isFull() {
        return size == MAX;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     *
     * @param key: 字符串
     * @return: 哈希值
     */
    public static int hash(String key) {
        final int base = 131;
        char[] ch = key.toCharArray();
        int ret = 0;
        for(char c: ch)
            ret = ((ret * base) % mod + c) % mod;
        return ret;
    }

    /**
     * 查找
     * @param key 要查询的键字符串
     * @return 对应日程编号, 若不存在键值对则返回 -1
     */
    public int get(String key) {
        for (int p = head[hash(key)]; p!= 0; p = data[p].next)
          if (data[p].key.equals(key)) return data[p].value;
        return -1;
    }

    /**
     * 修改
     * @param key 要修改的键字符串
     * @param value 键对应的值修改为value
     * @return 修改成功则返回value,修改失败则返回-1
     */
    public int update(String key, int value) {
        for (int p = head[hash(key)]; p != 0; p = data[p].next)
            if (data[p].key.equals(key)) {
                data[p].value = value;
                return value;
            }
        return -1;//不存在该键，修改失败
    }

    /**
     * 增加
     * @param key 要增加的键字符串
     * @param value 字符串对应的值
     * @return 增加成功返回value, 增加失败返回-1
     */
    public int add(String key, int value) {
        if (get(key) != -1) return -1;//已经存在该键，无法加入
        if (isFull()) return -1;//哈希表已满，无法加入
        int h = hash(key);
        Node n = new Node(head[h], value, key);

        while(dataOccupy[cnt]) cnt = (cnt + 1) % (MAX + 1);

        data[cnt] = n;
        head[h] = cnt;
        size ++;
        return value;
    }

    /**
     * 删除
     * @param key 要删除的键
     * @return 删除成功返回value, 删除失败返回-1
     */
    public int remove(String key) {
        int h = hash(key);

        for(int last = 0, p = head[h]; p != 0; last = p, p = data[p].next) {
            if( data[p].key.equals(key) ) {//找到要删除的键值对
                if(p == head[h]) //如果是链表头部
                    head[h] = data[p].next;
                else //不是链表头部
                    data[last].next = data[p].next;

                dataOccupy[p] = false;
                size --;
                return data[p].value;
            }
        }

        return -1;
    }

}
