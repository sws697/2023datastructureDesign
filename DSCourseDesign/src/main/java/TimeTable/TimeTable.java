/**
 * 日程管理
 */
package TimeTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

public class TimeTable {
    final static int MAX = 1024;
    final static int COURSE = 1;
    final static int EXTRA = 2;
    final static int TEMPO = 3;

    Splay splay;
    public TimeTable(Date startTime, Date endTime) {
        splay = new Splay(startTime, endTime);
    }

    /** 批量加入
     * 加入课程, 若选择的时间戳有其他课程则不会加入
     * 若有其他课外活动临时事务则覆盖
     * 若平衡树满则停止加入, 并返回-1
     */
    public int addCourse(String name, Date startTime, int hourLast, int weekLast, String location, String link, int tag) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        int cnt1 = weekLast;
        int ret = 1;

        while(cnt1 != 0) {
            Date date = cal.getTime();

            int cnt2 = hourLast;
            while(cnt2 != 0) {
                Date temp = cal.getTime();

                int id = splay.find(temp);
                if(id != -1) {//该时间戳有其他日程
                    if (splay.node[id].type != COURSE)//不是其他课程, 可以占用
                        splay.remove(temp);
                    ret = -1;
                }

                int ret2 = splay.insert(name, temp, location, link, COURSE, tag);//若该时间点有其他课程则不会插入
                if(ret2 == -1) ret = ret2;
                cal.add(Calendar.HOUR_OF_DAY, 1);
                cnt2 --;
            }

            cal.setTime(date);
            cal.add(Calendar.DATE, 7);
            cnt1 --;
        }
        return ret;
    }

    /** 批量加入
     * 加入课外活动, 若选择的时间戳有其他课程或课外活动则不会加入
     * 若有其他临时事务则覆盖
     * 若平衡树满则停止加入, 并返回-1
     */
    public int addExtra(String name, Date startTime, int weekLast, String location, String link, int tag) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        int cnt1 = weekLast;
        int ret = 1;

        while(cnt1 != 0) {
            Date temp = cal.getTime();

            int id = splay.find(temp);
            if(id != -1) {//该时间戳有其他日程
                if (splay.node[id].type == TEMPO)//是临时事务, 可以占用
                    splay.remove(temp);
                ret = -1;
            }

            int ret2 = splay.insert(name, temp, location, link, EXTRA, tag);
            if(ret2 == -1) ret = ret2;
            cal.add(Calendar.DATE, 7);
            cnt1 --;
        }
        return ret;
    }

    /** 单点加入
     * 加入临时事物, 若选择的时间戳有其他课程或课外活动则不会加入
     * 若有其他临时事务则共存
     * 若平衡树满则停止加入, 并返回-1
     */
    public int addTempo(String name, Date time, String location) {
        int ret = splay.insertTempo(name, time, location);
        if(ret == -1) return -1;
        return 1;
    }

    /** 批量删除
     * 删除从startTime开始到endTime为止的 所有名为name的课程
     */
    public void removeCourse(String name, Date startTime, Date endTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);

        while(cal.getTime().getTime() != endTime.getTime()) {
            Date date = cal.getTime();
            Event event = splay.search(date);
            if(event == null) continue;

            if( name.equals( event.name ) && event.type == COURSE)
                splay.remove(date);

            cal.add(Calendar.HOUR_OF_DAY, 1);
        }
    }

    /** 批量删除
     * 删除从startTime开始到endTime为止的 所有课程
     */
    public void clearCourse(Date startTime, Date endTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);

        while(cal.getTime().getTime() != endTime.getTime()) {
            Date date = cal.getTime();
            Event event = splay.search(date);
            if(event == null) continue;

            if(event.type == COURSE)
                splay.remove(date);

            cal.add(Calendar.HOUR_OF_DAY, 1);
        }
    }

    /** 批量删除
     * 删除从startTime开始到endTime为止的 所有名为name的课外活动
     */
    public void removeExtra(String name, Date startTime, Date endTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);

        while(cal.getTime().getTime() != endTime.getTime()) {
            Date date = cal.getTime();
            Event event = splay.search(date);
            if(event == null) continue;

            if( name.equals( event.name ) && event.type == EXTRA )
                splay.remove(date);

            cal.add(Calendar.HOUR_OF_DAY, 1);
        }
    }

    /** 批量删除
     * 删除从startTime开始到endTime为止的 所有课外活动
     */
    public void clearExtra(Date startTime, Date endTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);

        while(cal.getTime().getTime() != endTime.getTime()) {
            Date date = cal.getTime();
            Event event = splay.search(date);
            if(event == null) continue;

            if(event.type == EXTRA)
                splay.remove(date);

            cal.add(Calendar.HOUR_OF_DAY, 1);
        }
    }

    /** 批量删除
     * 删除从startTime开始到endTime为止的 所有名为name的临时事务
     */
    public void removeTempo(String name, Date startTime, Date endTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);

        while(cal.getTime().getTime() != endTime.getTime()) {
            Date date = cal.getTime();
            Event event = splay.search(date);
            if(event == null) continue;
            if(event.type != TEMPO) continue;

            for(int i = 0; i < event.tempo.size(); ++ i) {
                if(name.equals(event.tempo.get(i).name)) {
                    event.tempo.remove(i);
                    break;
                }
            }
            if(event.tempo.isEmpty())//如果该时间戳所有的临时事务都没有了, 可以把这个时间戳删除
                splay.remove(date);

            cal.add(Calendar.HOUR_OF_DAY, 1);
        }
    }

    /** 批量删除
     * 清除从startTime开始到endTime为止的 所有临时事务
     */
    public void clearTempo(Date startTime, Date endTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);

        while(cal.getTime().getTime() != endTime.getTime()) {
            Date date = cal.getTime();
            Event event = splay.search(date);
            if(event == null) continue;
            if(event.type != TEMPO) continue;

            splay.remove(date);

            cal.add(Calendar.HOUR_OF_DAY, 1);
        }
    }

    /** 批量删除
     *  清除时间范围内所有日程
     */
    public void clear(Date startTime, Date endTime) {
        splay.clear(startTime, endTime);
    }

    /**
     * 修改时间戳为time的课程的名称, 地点, 类型
     */
    public int updateCourse(Date time, String newName, String newLocation, String newLink, int newTag) {
        Event event = splay.search(time);
        if(event == null) return -1;//还有冲突问题
        if(event.type != COURSE) return -1;

        if(newName != null)
            event.name = newName;

        if(newLocation != null || newLink != null) {//如果两个有一个不为null, 说明需要修改
            event.location = newLocation;
            event.link = newLink;
        }

        if(newTag != 0)
            event.tag = newTag;
        return 1;
    }

    /**
     * 修改时间戳为time的课外活动的名称, 地点, 类型
     */
    public int updateExtra(Date time, String newName, String newLocation, String newLink, int newTag) {
        Event event = splay.search(time);
        if(event == null) return -1;
        if(event.type != EXTRA) return -1;

        if(newName != null)
            event.name = newName;

        if(newLocation != null || newLink != null) {//如果两个有一个不为null, 说明需要修改
            event.location = newLocation;
            event.link = newLink;
        }

        if(newTag != 0)
            event.tag = newTag;
        return 1;
    }

    /**
     * 修改时间戳为time, 名称为oldName的临时事务的 名称, 地点, 类型
     */
    public int updateTempo(Date time, String oldName, String newName, String newLocation) {
        Event event = splay.search(time);
        if(event == null) return -1;
        if(event.type != TEMPO) return -1;

        int ret = -1;
        for(int i = 0; i < event.tempo.size(); ++ i)
            if( oldName.equals(event.tempo.get(i).name) ) {
                event.tempo.get(i).name = newName;
                event.tempo.get(i).location = newLocation;
                ret = 1;
                break;
            }

        return ret;
    }

    /**
     * 收集名称为name的type日程
     */
    public void DFS(int u, String name, int type, ArrayList<Event> a) {
        if(u == 0) return;
        int lson = splay.node[u].son[0];
        int rson = splay.node[u].son[1];

        if(lson != 0)
            DFS(lson, name, type, a);

        if(type != TEMPO) {//类型为COURSE或者EXTRA
            if (name.equals(splay.node[u].name) && splay.node[u].type == type)
                a.add(splay.node[u]);
        }
        else {//类型为TEMPO
            ArrayList<Tempo> t = splay.node[u].tempo;
            for(int i = 0; i < t.size(); ++ i) {
                if( name.equals(t.get(i).name) ) {
                    //若该时间戳内存在名称为name的临时事务, 则将该时间戳加入ArrayList
                    a.add(splay.node[u]);
                    break;
                }
            }

        }
        if(rson != 0)
            DFS(rson, name, type, a);
    }

    /**
     * 收集type日程
     */
    public void DFS(int u, int type, ArrayList<Event> a) {
        if(u == 0) return;
        int lson = splay.node[u].son[0];
        int rson = splay.node[u].son[1];

        if(lson != 0)
            DFS(lson, type, a);
        if( splay.node[u].type == type )
            a.add(splay.node[u]);
        if(rson != 0)
            DFS(rson, type, a);
    }

    /**
     * 收集type类日程, 类型为tag的Event
     */
    public void DFS(int u, int tag, int type, ArrayList<Event> a) {
        if(u == 0) return;
        int lson = splay.node[u].son[0];
        int rson = splay.node[u].son[1];

        if(lson != 0)
            DFS(lson, type, a);
        if( splay.node[u].type == type && splay.node[u].tag == tag)
            a.add(splay.node[u]);
        if(rson != 0)
            DFS(rson, type, a);
    }

    /**
     * 收集所有日程
     */
    public void DFS(int u, ArrayList<Event> a) {
        if(u == 0) return;
        int lson = splay.node[u].son[0];
        int rson = splay.node[u].son[1];

        if(lson != 0) DFS(lson, a);
        a.add(splay.node[u]);
        if(rson != 0) DFS(rson, a);
    }

    /**
     * 查询从startTime开始至endTime为止的, 名称为name的所有课程
     * 返回照时间排好序的ArrayList<Event>
     */
    public ArrayList<Event> searchCourseName(String name, Date startTime, Date endTime) {
        int u = splay.preHandle(startTime, endTime);
        ArrayList<Event> a = new ArrayList<>();
        DFS(u, name, COURSE, a);
        return a;
    }

    /**
     * 查询从startTime开始至endTime为止的, 类型为tag的所有课程
     * 返回照时间排好序的ArrayList<Event>
     */
    public ArrayList<Event> searchCourseTag(int tag, Date startTime, Date endTime) {
        int u = splay.preHandle(startTime, endTime);
        ArrayList<Event> a = new ArrayList<>();
        DFS(u, tag, COURSE, a);
        return a;
    }

    /**
     * 查询从startTime开始至endTime为止的所有课程
     * 返回照时间排好序的ArrayList<Event>
     */
    public ArrayList<Event> displayCourse(Date startTime, Date endTime) {
        int u = splay.preHandle(startTime, endTime);
        ArrayList<Event> a = new ArrayList<>();
        DFS(u, COURSE, a);
        return a;
    }

    /**
     * 查询从startTime开始至endTime为止的, 名称为name的所有课外活动
     * 返回照时间排好序的ArrayList<Event>
     */
    public ArrayList<Event> searchExtraName(String name, Date startTime, Date endTime) {
        int u = splay.preHandle(startTime, endTime);
        ArrayList<Event> a = new ArrayList<>();
        DFS(u, name, EXTRA, a);
        return a;
    }

    /**
     * 查询从startTime开始至endTime为止的, 类型为tag的所有课外活动
     * 返回照时间排好序的ArrayList<Event>
     */
    public ArrayList<Event> searchExtraTag(int tag, Date startTime, Date endTime) {
        int u = splay.preHandle(startTime, endTime);
        ArrayList<Event> a = new ArrayList<>();
        DFS(u, tag, EXTRA, a);
        return a;
    }

    /**
     * 查询从startTime开始至endTime为止的所有课外活动
     * 返回照时间排好序的ArrayList<Event>
     */
    public ArrayList<Event> displayExtra(Date startTime, Date endTime) {
        int u = splay.preHandle(startTime, endTime);
        ArrayList<Event> a = new ArrayList<>();
        DFS(u, EXTRA, a);
        return a;
    }

    /**
     * 查询从startTime开始至endTime为止的, 名称为name的所有临时事务
     * 返回照时间排好序的ArrayList<Event>
     */
    public ArrayList<Event> searchTempoName(String name, Date startTime, Date endTime) {
        int u = splay.preHandle(startTime, endTime);
        ArrayList<Event> a = new ArrayList<>();
        DFS(u, name, TEMPO, a);
        return a;
    }

    /**
     * 查询从startTime开始至endTime为止的所有临时事务
     * 返回照时间排好序的ArrayList<Event>
     */
    public ArrayList<Event> displayTempo(Date startTime, Date endTime) {
        int u = splay.preHandle(startTime, endTime);
        ArrayList<Event> a = new ArrayList<>();
        DFS(u, TEMPO, a);
        return a;
    }

    /**
     * 查询从startTime开始至endTime为止的所有日程
     * 返回照时间排好序的ArrayList<Event>
     */
    public ArrayList<Event> displayAll(Date startTime, Date endTime) {
        int u = splay.preHandle(startTime, endTime);
        ArrayList<Event> a = new ArrayList<>();
        DFS(u, a);
        return a;
    }

    /**
     * 查询时间空闲状态，若该小时空闲，返回true; 否则返回false
     * @param time
     * @return
     */
    public boolean available(Date time) {
        if(splay.find(time) == -1) return true;
        else return false;
    }

    public ArrayList<Date> searchAvailable(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);

        cal.add(Calendar.HOUR_OF_DAY, -time.getHours() + 6);
        ArrayList<Date> a = new ArrayList<>();
        int cnt1 = 0, cnt2 = 6;

        while(cnt1 < 3 && cnt2 < 22) {
            cnt2 ++;
            if(cal.getTime() == time) continue;
            if(!available(cal.getTime())) continue;

            cnt1 ++;
            a.add(cal.getTime());
            cal.add(Calendar.HOUR_OF_DAY, 1);
        }
        return a;
    }

    public static void main(String[] args) {
        Date date1 = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.DATE, 60);
        Date date2 = cal.getTime();
        TimeTable timeTable = new TimeTable(date1, date2);

        cal.add(Calendar.HOUR_OF_DAY, -5);
        date2 = cal.getTime();
        cal.setTime(date1);
        cal.add(Calendar.HOUR_OF_DAY, 2);
        date1 = cal.getTime();

        timeTable.addCourse("datastructure", date1, 1, 1, "ONE", null, 1);
        cal.add(Calendar.HOUR_OF_DAY, 24);
        date1 = cal.getTime();
        timeTable.addExtra("shower", date1, 1, "TWO", null, 1);
        cal.add(Calendar.HOUR_OF_DAY, -24);
        date1 = cal.getTime();
        ArrayList<Event> event = timeTable.displayCourse(date1, date2);

        System.out.println(event.size());
        for(int i = 0; i < event.size(); ++ i) {
            System.out.println(event.get(i).name);
            System.out.println(event.get(i).time);
        }
    }
}
