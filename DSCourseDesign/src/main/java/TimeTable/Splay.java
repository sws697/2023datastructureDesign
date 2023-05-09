package TimeTable;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;

public class Splay {
    /**
     * 平衡树里首先会插入一个负无穷结点和一个正无穷结点, 以便相关边界条件的判断
     */
    final static int MAX = 1024;
    final static int COURSE = 1;
    final static int EXTRA = 2;
    final static int TEMPO = 3;

    int cnt;//新分配的结点编号
    int size;//结点个数
    int root;//根节点编号
    boolean[] indexOccupy;
    Event[] node;

    public Splay(Date startTime, Date endTime) {
        cnt = 0;
        size = 0;
        root = 0;
        indexOccupy = new boolean[MAX + 5];
        indexOccupy[0] = true;
        node = new Event[MAX + 5];
        for(Event elem:  node) elem = new Event();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        Date date = cal.getTime();
        insert(null , date, null, 0, 0);//插入负无穷时间戳
        insert(null , endTime, null, 0, 0);//插入正无穷时间戳
    }

    void pushup(int u) {//更新子树大小
        if(u == 0) return;
        int lson = node[u].son[0];
        int rson = node[u].son[1];
        node[u].size = 1;
        if(lson != 0)
            node[u].size += node[lson].size;
        if(rson != 0)
            node[u].size += node[rson].size;
    }

    int addCnt() {
        while(indexOccupy[cnt])
            cnt = (cnt + 1) % (MAX + 1);
        return cnt;
    }

    /**
     * 判断结点方向
     * @param x 结点编号
     * @return 若该结点为父亲的右儿子, 返回1; 否则返回0
     */
    int chk(int x) {
        int f = node[x].fa;
        if(node[f].son[1] == x)
            return 1;
        else
            return 0;
    }

    /**
     * 将结点向上旋转(包括左旋和右旋)
     * @param x
     */
    protected void rotate(int x) {
        int y = node[x].fa, z = node[y].fa;
        int kx = chk(x);
        int ky = chk(y);
        int t = node[x].son[kx ^ 1];

        node[y].son[kx] = t;
        node[y].fa = x;
        pushup(y);

        node[x].son[kx ^ 1] = y;
        node[x].fa = z;
        pushup(x);

        node[z].son[ky] = x;
        node[t].fa = y;
    }

    /**
     * 将 u splay 到 goal 下方
     * @param u
     * @param goal
     */
    protected void splay(int u, int goal) {
        while(node[u].fa != goal) {
            int v = node[u].fa;
            int w = node[v].fa;

            /**可旋转两次**/
            if(w != goal) {
                /**如果u的方向和v一致, 则需要先旋转v, 再旋转u, 才能保证左右平衡**/
                if(chk(u) == chk(v)) {
                    rotate(v);
                    rotate(u);
                }
                /**如果uv方向不一致, 只需要连续选装两次u即可**/
                else {
                    rotate(u);
                    rotate(u);
                }
            }
            /**只能旋转一次**/
            else rotate(u);
        }
        if(goal == 0) root = u;
    }

    /**
     * 查找time时间的日程
     * @param time
     * @return 查询成功返回编号, 查询失败返回-1
     */
    int find(Date time) {//由于一开始会插入负无穷和正无穷结点, 平衡树不会为空
        int u = root;
        while(node[u].time.getTime() != time.getTime()) {
            if(node[u].time.getTime() > time.getTime()) {//往左儿子走
                if (node[u].son[0] != 0) //左儿子存在
                    u = node[u].son[0];
                else return -1;//不存在, 说明没有, 直接返回-1
            }
            else {//否则往右儿子走
                if (node[u].son[1] != 0)
                    u = node[u].son[1];
                else return -1;
            }
        }
//        splay(u, 0);
        return u;
    }

    /**
     * 在平衡树中插入日程结点, 并splay到根
     * @param name 名称
     * @param time 时间
     * @param location 地点
     * @param type 课程 or 课外 or 临时
     * @param tag 类型(临时事务没有类型, 默认为0)
     * @return 插入成功返回1, 失败返回-1
     */
    int insert(String name, Date time, String location, int type, int tag) {
        if(find(time) != -1) return -1;//该时间戳已被占用, 插入失败
        if(size == MAX) return -1;//平衡树已满, 插入失败

        int u = root;
        if(size == 0) {//平衡树为空，创建根结点
            u = addCnt();
            node[u].name = name;
            node[u].time = time;
            node[u].location = location;
            node[u].type = type;
            node[u].tag = tag;

            node[u].son[0] = 0; node[u].son[1] = 0; node[u].fa = 0;
            node[u].size = 1;
            indexOccupy[u] = true;
            size ++;
            return 1;
        }
        int v = 0;//u的父亲
        int k = 0;//node[v].son[k] == u

        while(u != 0) {
            if(node[u].time.getTime() > time.getTime()) {
                v = u;
                u = node[u].son[0];
                k = 0;
            }
            else {
                v = u;
                u = node[u].son[1];
                k = 1;
            }
        }

        u = addCnt();
        node[u].name = name;
        node[u].time = time;
        node[u].location = location;
        node[u].type = type;
        node[u].tag = tag;

        node[u].fa = v;
        node[u].son[0] = 0; node[u].son[1] = 0;
        node[u].size = 1;
        node[v].son[k] = u;
        pushup(v);

        indexOccupy[u] = true;
        size ++;
        splay(u, 0);
        return 1;
    }

    int insertTempo(String name, Date time, String location) {
        if(size == MAX && find(time) == -1) return -1;//平衡树已满且需要增加新结点, 插入失败

        int u = root;
        if(size == 0) {//平衡树为空，创建根结点
            u = addCnt();
            Tempo t = new Tempo(name, location);
            node[u].tempo.add(t);

            node[u].time = time;
            node[u].type = TEMPO;
            node[u].tag = 0;

            node[u].son[0] = 0; node[u].son[1] = 0; node[u].fa = 0;
            node[u].size = 1;
            indexOccupy[u] = true;
            size ++;
            return 1;
        }

        int v = 0;//u的父亲
        int k = 0;//node[v].son[k] == u

        while(u != 0) {
            if(node[u].time.getTime() == time.getTime()) {//该时间戳已被占用
                if(node[u].type == COURSE || node[u].type == EXTRA)//该时间戳为课程或临时事务, 无法插入
                    return -1;//直接返回-1
                //否则可以加入ArrayList
                Tempo t = new Tempo(name, location);
                node[u].tempo.add(t);

                return 1;
            }
            else if(node[u].time.getTime() > time.getTime()) {
                v = u;
                u = node[u].son[0];
                k = 0;
            }
            else {
                v = u;
                u = node[u].son[1];
                k = 1;
            }
        }

        u = addCnt();
        Tempo t = new Tempo(name, location);
        node[u].tempo.add(t);
        node[u].time = time;
        node[u].type = TEMPO;
        node[u].tag = 0;

        node[u].fa = v;
        node[u].son[0] = 0; node[u].son[1] = 0;
        node[u].size = 1;
        node[v].son[k] = u;
        pushup(v);

        indexOccupy[u] = true;
        size ++;
        splay(u, 0);
        return 1;
    }

    /**
     * 寻找time时间戳的前驱日程结点
     * @param time
     * @return
     */
    int pre(Date time) {
        int u = find(time);
        splay(u, 0);
        u = node[u].son[0];
        while(node[u].son[1] != 0) u = node[u].son[1];
        return u;
    }

    /**
     * 寻找time时间戳的后继日程结点
     * @param time
     * @return
     */
    int nex(Date time) {
        int u = find(time);
        splay(u, 0);
        u = node[u].son[1];
        while(node[u].son[0] != 0) u = node[u].son[0];
        return u;
    }

    /**
     * 删去时间戳为time的日程结点
     * @param time
     * @return 删除成功返回1, 否则返回-1
     */
    int remove(Date time) {
        if(find(time) == -1) return -1;//不存在该时间戳, 删除失败

        int p = pre(time), q = nex(time);
        splay(p, 0); splay(q, p);
        indexOccupy[node[q].son[0]] = false;
        node[q].son[0] = 0;
        pushup(q);
        pushup(p);
        size --;
        if(size == 0) root = 0;
        return 1;
    }

    /**
     * 将从startTime开始到endTime为止时间范围内的所有日程
     * 放在根结点的右儿子的左子树
     * 并返回根结点右儿子的左儿子的编号
     */
    public int preHandle(Date startTime, Date endTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        cal.add(Calendar.HOUR_OF_DAY, -1);
        startTime = cal.getTime();

        int id1 = find(startTime);
        if (id1 == -1) id1 = pre(startTime);

        int id2 = find(startTime);
        if (id2 == -1) id2 = nex(endTime);

        splay(id1, id2);

        int u = root;
        u = node[u].son[1];
        u = node[u].son[0];
        return u;
    }

    /**
     * 将从startTime开始到endTime为止时间范围内的所有日程清空
     */
    public void clear(Date startTime, Date endTime) {
        int u = preHandle(startTime, endTime);
        if(u == 0) return;

        u = node[u].fa;
        node[u].son[0] = 0;
        pushup(u);
        pushup(root);
    }

    Event search(Date time) {
        int i = find(time);
        if(i == -1) return null;
        splay(i, 0);//将访问到的结点旋转至根结点
        return node[i];
    }

}
