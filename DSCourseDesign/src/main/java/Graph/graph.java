/**
 * 图论部分相关功能
 */
package Graph;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class graph {
    /**
     * 基本数据
     */
    int MAX = 1024;

    Edge[] edge;
    Node[] node;
    int edge_num;
    int[] head;//链式前向星结构
    public int[] path;//存放输出路径
    public int[] dis;

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
    };

    /**
     * 构造器初始化
     */
    public graph() {
        edge_num = 0;
        edge = new Edge[MAX * 2];
        node = new Node[MAX];
        head = new int[MAX];
        path = new int[MAX];
        dis = new int[MAX];
        for(int i = 0; i < MAX; ++ i) dis[i]= Integer.MAX_VALUE;
    }

    /**
     * 加边
     */
    public void add(int u, int v, String name) {
        edge[++ edge_num].next = head[u];
        edge[edge_num].to = v;
        edge[edge_num].name = name;
        head[u] = edge_num;
    }

    /**
     * 堆优化Dijkstra单源最短路方法
     * Priorityqueue后续可能会自己实现，先用容器
     */
    public void Dijkstra(int x) {//以x为源点进行Dijkstra最短路算法
        Queue< pair > q = new PriorityQueue<>();
        boolean[] vis = new boolean[MAX];
        for(int i = 0; i < MAX; i ++) dis[i] = Integer.MAX_VALUE;

        dis[x] = 0;
        pair p = new pair(x, dis[x]);
        q.add( p );

        while(!q.isEmpty()) {
            pair u = new pair(q.poll());
            if(vis[u.first]) continue;
            vis[u.first] = true;//标记为加入S集合的点

            for(int i = head[u.first]; i != 0; i = edge[i].next) {
                if(dis[u.first] + edge[i].len < dis[edge[i].to]) {
                    dis[ edge[i].to ] = dis[u.first] + edge[i].len;
                    pair v = new pair(edge[i].to, dis[ edge[i].to ]);
                    q.add(v);
                }
            }
        }

    }
}
