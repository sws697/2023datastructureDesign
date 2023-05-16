package TimeTable;

import java.util.ArrayList;
import java.util.Date;

public class Event {//事务时间戳
    String name;//课程或临时事务名称
    ArrayList<Tempo> tempo;//由于同一个时间戳可存在多个临时事务, 故选择用ArrayList来存储临时事务
    Date time;//时间戳
    String location;//课程地点名称
    /** type:
     *  课程 or 课外活动 or 临时事务
     */
    int type;

    /** tag:
     * 课程: 上课 or 考试
     * 课外活动: 集体活动 or 个人活动
     */
    int tag;

    /**
     * 平衡树部分
     */
    int fa;//平衡书中父亲编号
    int[] son;//两个儿子的编号
    int size;//子树大小

    public Event() {
        name = new String();
        time = new Date();
        location = new String();
        type = 0;
        tag = 0;
        fa = 0;
        son = new int [2];
        size = 1;
    }

    public Date getTime() {
        return time;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Tempo> getTempo() {
        return tempo;
    }
}
