/**
 * 图论部分相关功能
 */
package Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Graph {
    /**
     * 基本数据
     */
    final int MAX = 1024;
    final int MAXE = 10240;

    Edge[] edge;
    Node[] node;
    int n;
    int node_num;
    int edge_num;
    int[] head;//链式前向星结构

    public int[][] distance;//存放最短距离
    public ArrayList<Integer>[][] path;//存放最短路径
    public int[] dis;

    HashTable hashtable;
    /**
     * 构造器初始化
     */
    public Graph() {
        node_num = 0;
        edge_num = 0;

        node = new Node[MAX + 5];
        for(Node elem: node) elem = new Node();

        edge = new Edge[MAXE + 5];
        for(Edge elem: edge) elem = new Edge();

        head = new int[MAX + 5];

        distance = new int[MAX + 5][MAX + 5];
        dis = new int[MAX];
        for(int i = 0; i <= MAX; ++ i) dis[i]= Integer.MAX_VALUE;

        ArrayList<Integer>[][] path = new ArrayList[MAX + 5][MAX + 5];
        for(int i = 0; i <= MAX; ++ i)
            for(int j = 0; j <= MAX; ++ j)
                path[i][j] = new ArrayList<Integer>();

        hashtable = new HashTable();
    }


    /**
     * 为Dijkstra服务的pair类
     */
    class pair {
        int first;
        int second;

        public pair() {
            first = 0;
            second = 0;
        }

        public pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public pair(pair p) {
            this.first = p.first;
            this.second = p.second;
        }
    }

    /**
     * pair的比较器
     */
    class pairComparator implements Comparator<pair> {
        public int compare(pair a, pair b) {
            return a.second - b.second;
        }
    }

    /**
     * 加边
     */
    public void add(int u, int v, int len) {
        edge[++ edge_num].next = head[u];
        edge[edge_num].to = v;
        edge[edge_num].len = len;
        head[u] = edge_num;
    }

    /**
     * 堆优化Dijkstra单源最短路方法
     * Priorityqueue后续可能会自己实现，先用容器
     */
    public void Dijkstra(int x) {//以x为源点进行Dijkstra最短路算法
        Queue< pair > q = new PriorityQueue<>();
        boolean[] vis = new boolean[MAX];
        for(int i = 0; i <= node_num; i ++) dis[i] = Integer.MAX_VALUE;
        int[] last = new int [MAX];

        dis[x] = 0;
        pair p = new pair(x, dis[x]);
        q.add(p);

        while(!q.isEmpty()) {
            pair u = q.poll();
            if(vis[u.first]) continue;
            vis[u.first] = true;//标记为加入S集合的点

            for(int i = head[u.first]; i != 0; i = edge[i].next) {
                if(dis[u.first] + edge[i].len < dis[edge[i].to]) {
                    dis[ edge[i].to ] = dis[u.first] + edge[i].len;
                    pair v = new pair(edge[i].to, dis[ edge[i].to ]);
                    q.add(v);

                    last[edge[i].to] = u.first;//last数组协助求出最短路径
                }
            }
        }

        /**
         * 记录起点x到每个点i的最短路径和最短距离
         */
        for(int i = 1; i <= node_num; ++ i) {
            distance[x][i] = dis[i];
            int temp = i;
            while(temp != x) {
                path[x][i].add(temp);
                temp = last [temp];
            }
            path[x][i].add(temp);
        }

    }

    /**
     * 初始化任意两点间的最短距离
     */
    public void initShortestPath() {
        for(int i = 1; i <= node_num; ++ i)
            Dijkstra(i);
    }

    /**
     * 根据名称创建建筑物
     * 若node数组已满则返回-1
     */
    public int createBuilding(String name,int x,int y) {
        node[++ node_num].name = name;
        node[node_num].setPoint(x,y);
        int ret = hashtable.add(name, node_num);
        if(ret == -1) return -1;
        else return 1;
    }

    /**
     * 在名称为name1的建筑物 和 名称为name2的建筑物之间建立长度为len的道路
     * 若存在不合法名称或len为负数则返回-1
     * 若Edge数组已满则返回-1
     */
    public int createRoad(String name1, String name2, int len) {
        if(len < 0) return -1;

        int id1 = hashtable.get(name1);
        int id2 = hashtable.get(name2);
        if(id1 == -1 || id2 == -1) return -1;

        if(edge_num == MAXE) return -1;

        add(id1, id2, len);
        add(id2, id1, len);
        return 1;
    }

    /**
     *  查询从名为start的建筑到名为end的建筑的最短路
     *  若名称不合法则返回null
     *  返回ArrayList<String>, 包括起点和终点
     */
    public ArrayList<Node> shortestPath(String start, String end) {
        ArrayList<Node> a = new ArrayList<>();
        int id1 = hashtable.get(start);
        int id2 = hashtable.get(end);
        if(id1 == -1 || id2 == -1) return null;

        for(int i = path[id1][id2].size() - 1; i >= 0; i --)
            a.add(node[ path[id1][id2].get(i) ]);
        return a;
    }

    int checkBit(int x, int i) {//检查二进制数x的第i位为0还是1, 并返回结果
        int num = 1;
        while (i != 0) {
            num <<= 1;
            i --;
        }
        if((x & num) == 0)
            return 0;
        else
            return 1;
    }

    int setBit(int x, int i, int k) {//把二进制数x的第i位设置为k, k为0或1; 返回结果
        int num = 1;
        while (i != 0) {
            num <<= 1;
            i --;
        }

        if(k == 0) //要修改成0
            if((x & num) != 0)//这一位为1, 需要修改
                return x ^ num;
        if(k == 1) //要修改成1
            if((x & num) == 0)//这一位为0, 需要修改
                return x ^ num;
        return x;//不需要修改的返回原数即可
    }

    /** 途经最短路: 状压DP
     * 若名称不合法则返回null
     * start是起点, passBy为需要中途路过的建筑物集合, 最后回到start
     * 返回ArrayList<String>, 包括起点和终点
     */
    public ArrayList<String> pathByTheWay(String start, ArrayList<String> passBy) {
        int id = hashtable.get(start);
        if(id == -1) return null;

        int[][] f = new int[MAX + 5][MAX + 5];//设f[s][u]为从id1出发到u, 经过的点集为s的最短路程
        for(int i = 0; i <= MAX; ++ i)
            for(int j = 0; j <= MAX; ++ j)
                f[i][j] = Integer.MAX_VALUE;
        int[][] last = new int[MAX + 5][MAX + 5];//用来记录具体路径

        int[] mapping = new int[MAX + 5];//建立结点编号到二进制数位置的映射
        ArrayList<Integer> a = new ArrayList<>();
        a.add(id);
        mapping[id] = 0;

        for(int i = 0; i < passBy.size(); ++ i) {
            int ind = hashtable.get( passBy.get(i) );
            if(ind == -1) return null;

            a.add( id );
            mapping[id] = i + 1;
        }
        f[1][id] = 0;

        for(int i = 2; i < (int)Math.pow(2, a.size()) - 1; ++ i) {//先枚举状态
            for(int jj = 0; jj < a.size(); ++ jj) {//再枚举最后到达的点
                int j = a.get(jj);//j为结点编号
                if(checkBit(i, mapping[j]) == 0) continue;//非法状态, 跳过

                for(int kk = 0; kk < a.size(); ++ kk) {//枚举从k到达j
                    int state = setBit(i, mapping[j], 0);
                    int k = a.get(kk);//k为结点编号
                    if (checkBit(state, mapping[k]) == 0) continue;//非法状态 跳过

                    if(f[state][k] + distance[k][j] < f[i][j]) {
                        f[i][j] = f[state][k] + distance[k][j];
                        last[i][j] = k;
                    }
                }
            }
        }

        int end = 0, minx = Integer.MAX_VALUE;
        for(int ii = 1; ii < a.size(); ++ ii) {//枚举终点
            int i = a.get(ii);
            if(f[(int)Math.pow(2, a.size()) - 1][i] + distance[i][id] < minx) {
                end = i;
                minx = f[(int) Math.pow(2, a.size()) - 1][i] + distance[i][id];
            }
        }
        ArrayList<String> ans1 = new ArrayList<>();
        ans1.add(start);

        int state = (int)Math.pow(2, a.size() - 1);
        int u = end;
        while(state != 1) {
            ans1.add( node[u].name );
            int t = u;
            u = last[state][u];
            state = setBit(state, mapping[t], 0);
        }
        ans1.add( start );

        ArrayList<String> ans2 = new ArrayList<>();
        for(int i = ans1.size() - 1; i >= 0; -- i)
            ans2.add(ans1.get(i));

        return ans2;
    }
}
