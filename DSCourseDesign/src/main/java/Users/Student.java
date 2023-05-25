package Users;

import Dao.GetSession;
import Graph.*;
import TimeTable.*;
import Users.tool.EventForRec;
import VTime.Clock;
import VTime.VirtualTime;
import mapper.EventMapper;
import mapper.GraphMapper;
import mapper.StudentMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.w3c.dom.CDATASection;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;



/**
 * 学生类，提供登录，查询等接口
 *
 * @author maxiaotiao
 */
public class Student {
    /**
     * 为0表示当前未登录成功
     */
    private static int sid = 0;
    private static String password = "0";
    /**
     * 目前暂定学生初始位置为学五
     */
    private static String location;

    private static TimeTable timeTable = new TimeTable(new Date(2023, 1, 17), new Date(2023, 5, 19));

    private static Graph graph = new Graph();

    /**
     * @return 返回当前系统用户Id（学号）
     */
    public static int getSid() {
        return Student.sid;
    }

    public void setId(int id) {
        Student.sid = id;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Student.password = password;
    }


    public static void setLocation(String location) {
        Student.location = location;
    }

    /**
     * 用户登录
     * 登录成功后执行初始化操作，将用户所有课程数据从
     *
     * @param id       学号
     * @param password 密码
     * @return 登录成功返回true
     */
    public static boolean login(int id, String password) {
        SqlSession session = GetSession.getSesssion();
        StudentMapper mapper = session.getMapper(StudentMapper.class);
        Student student = mapper.select(id, password);
        session.close();
        if (student != null) {
            Student.sid = id;
            Student.password = password;
            Student.setLocation("DOR5");
            splayInit(Student.sid);
            graphInit();
            return true;
        }
        return false;
    }

    /**
     * 从数据库获取当前用户的所有日程信息，添加到splay中，用了EventForRec作为中间类一次性接收
     *
     * @param id
     * @return
     */
    public static ArrayList<EventForRec> getEvents(int id) {
        SqlSession session = GetSession.getSesssion();
        EventMapper mapper = session.getMapper(EventMapper.class);
        ArrayList<EventForRec> events = mapper.getEvents(id);
        session.close();
        return events;
    }

    /**
     * 在用户登录成功后初始化splay，后面用户的所有操作全部从splay获取，不再与数据库有交互
     *
     * @param sid
     */
    public static void splayInit(int sid) {
        ArrayList<EventForRec> events = getEvents(sid);
        Iterator<EventForRec> event = events.iterator();
        while (event.hasNext()) {
            EventForRec event1 = event.next();
            switch (event1.getType()) {
                case 1:
                    timeTable.addCourse(event1.getName(), event1.getStartTime(),
                            event1.getHourLast(), event1.getWeekLast(), event1.getLocation(),
                            event1.getLink(), event1.getTag());
                    break;
                case 2:
                    timeTable.addExtra(event1.getName(), event1.getStartTime(),
                            event1.getWeekLast(), event1.getLocation(), event1.getLink(), event1.getTag());
                    break;
                case 3:
                    timeTable.addTempo(event1.getName(), event1.getStartTime(), event1.getLocation());
                    break;
            }
        }
    }

    /**
     * 每日提醒查询第二天所有课程
     *
     * @return
     */
    public static ArrayList<Event> DailyCourseRemind() {
        Calendar calendar = VirtualTime.getCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, VirtualTime.getDay() + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        Date startTime = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        Date endTime = calendar.getTime();
        return timeTable.displayCourse(startTime, endTime);
    }

    /**
     * 每日提醒查询第二天所有的课外活动
     *
     * @return
     */
    public static ArrayList<Event> DailyExtraRemind() {
        Calendar calendar = VirtualTime.getCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, VirtualTime.getDay() + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        Date startTime = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        Date endTime = calendar.getTime();
        return timeTable.displayExtra(startTime, endTime);
    }

    /**
     * 课程临近提醒
     *
     * @return
     */
    public static Event CourseAdvanceRemind() {
        Calendar calendar = VirtualTime.getCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, VirtualTime.getHours() + 1);
        Date startTime = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, VirtualTime.getHours() + 4);
        Date endTime = calendar.getTime();
        ArrayList<Event> events = timeTable.displayCourse(startTime, endTime);
        if (events.size() != 0){
            Event event = events.get(0);
            if (event.getTime().equals(startTime)) {
                return event;
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * 获取当前正在进行的活动
     *
     * @return
     */
    public static Event getCurrentEvent() {
        Calendar calendar = VirtualTime.getCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, VirtualTime.getHours());
        Date startTime = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, VirtualTime.getHours() + 1);
        Date endTime = calendar.getTime();
        ArrayList<Event> events = timeTable.displayCourse(startTime, endTime);
        Event event = events.get(0);
        if (event.getTime().equals(startTime)) {
            return event;
        } else {
            return null;
        }
    }

    public static TimeTable getTimeTable() {
        return timeTable;
    }

    public static boolean addExtra(String name, String location,
                                Date startTime, int tag,
                                int type, int weekLast,
                                int hourLast, int sid,
                                String link) {
        if (timeTable.addExtra(name, startTime, weekLast, location, link, tag) == 1) {
            SqlSession sesssion = GetSession.getSesssion();
            StudentMapper mapper = sesssion.getMapper(StudentMapper.class);
            mapper.addExtra(name, location, startTime, tag, type, weekLast, hourLast, sid, link);
            sesssion.close();
            return true;
        }
        return false;

    }

    public static boolean addTempo(String name, String location,
                                Date startTime, int tag,
                                int type, int weekLast,
                                int hourLast, int sid,
                                String link) {
        if (timeTable.addTempo(name, startTime, location) == 1) {
            SqlSession sesssion = GetSession.getSesssion();
            StudentMapper mapper = sesssion.getMapper(StudentMapper.class);
            mapper.addTempo(name, location, startTime, tag, type, weekLast, hourLast, sid, link);
            sesssion.close();
            return true;
        }
        return false;
    }

    public static boolean updateExtra(Date startTime, String name, String newLocation, int sid, int newTag, String newLink) {
        if (timeTable.updateExtra(startTime, name, newLocation, newLink, newTag) != -1) {
            SqlSession sesssion = GetSession.getSesssion();
            StudentMapper mapper = sesssion.getMapper(StudentMapper.class);
            mapper.updateExtra(startTime, name, newLocation, sid, newTag, newLink);
            sesssion.close();
            return true;
        }
        return false;
    }

    public static boolean updateTempo(Date startTime, String oldName, String newName, String newLocation, int sid, int newTag, String newLink) {
        if (timeTable.updateTempo(startTime, oldName, newName, newLocation) != -1) {
            SqlSession sesssion = GetSession.getSesssion();
            StudentMapper mapper = sesssion.getMapper(StudentMapper.class);
            mapper.updateTempo(startTime, oldName, newName, newLocation, sid, newTag, newLink);
            sesssion.close();
            return true;
        }
        return false;
    }

    /**
     * 默认一个课外活动要删就全删
     *
     * @param startTime
     * @param endTime
     * @param name
     * @param sid
     */
    public static void removeExtra(Date startTime, Date endTime, String name, int sid) {
        timeTable.removeExtra(name, startTime, endTime);
        SqlSession sesssion = GetSession.getSesssion();
        StudentMapper mapper = sesssion.getMapper(StudentMapper.class);
        mapper.deleteExtra(name, sid);
        sesssion.close();
    }

    public static void removeTempo(Date startTime, Date endTime, String name, int sid) {
        timeTable.removeTempo(name, startTime, endTime);
        SqlSession sesssion = GetSession.getSesssion();
        StudentMapper mapper = sesssion.getMapper(StudentMapper.class);
        mapper.deleteTempo(name, sid);
        sesssion.close();
    }

    public static void graphInit() {
        SqlSession sesssion = GetSession.getSesssion();
        GraphMapper mapper = sesssion.getMapper(GraphMapper.class);
        ArrayList<Node> nodes = mapper.getNodes();
        ArrayList<Road> roads = mapper.getRoads();
        for(Node node:nodes){
            graph.createBuilding(node.getName());
        }
        for(Road road:roads){
            graph.createRoad(road.getName1(),road.getName1(),road.getLen());
        }
        graph.initShortestPath();

    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
