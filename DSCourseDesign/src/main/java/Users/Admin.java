package Users;

import Dao.GetSession;
import TimeTable.TimeTable;
import Users.tool.EventForRec;
import mapper.AdminMapper;
import mapper.StudentMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import TimeTable.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * 管理员类，目前未具体实现
 *
 * @author maxiaotiao
 */
public class Admin {
    private String username = "admin";
    private String password = "admin";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public boolean login(String username, String password) {
        if (this.username.equals(username) & this.password.equals(password)) {
            return true;
        } else return false;
    }

    public void addStu(int sid, String password, String location) {
        SqlSession session = GetSession.getSesssion();
        AdminMapper mapper = session.getMapper(AdminMapper.class);
        mapper.addStu(sid, password, location);
        session.commit();
        session.close();
    }

    /**
     * 给学生添加课程,如果有冲突的课外活动或者临时事务，覆盖，添加课程要一个一个学生添加，数据库里面不用删除，因外
     * 在读取的时候还是会覆盖掉课外活动或者临时事务
     */
    public boolean addCourse(String name, String lcoation,
                             Date startTime, int tag,
                             int type, int weekLast,
                             int hourLast,
                             int sid, String link) {
        SqlSession session = GetSession.getSesssion();
        AdminMapper adminMapper = session.getMapper(AdminMapper.class);

        TimeTable timeTable = new TimeTable(new Date(70, 1,
                17), new Date(200, 5, 19));
        ArrayList<EventForRec> events = Student.getEvents(sid);
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

        if (timeTable.addCourse(name, startTime, hourLast, weekLast, lcoation, lcoation, tag) != -1) {
            adminMapper.addCourse(name, lcoation, startTime, tag, type, weekLast, hourLast, sid, link);
            session.commit();
            session.close();
            return true;
        } else {
            return false;
        }
    }


    /**
     * 删除课程不受冲突影响
     *
     * @param name
     * @param sid
     */
    public void removeCourse(String name, int sid) {
        SqlSession session = GetSession.getSesssion();
        AdminMapper mapper = session.getMapper(AdminMapper.class);
        mapper.removeCourse(name, sid);
        session.commit();
        session.close();
    }


    /**
     * 修改地址不会有冲突
     *
     * @param name
     * @param Location
     */
    public void updateCourseLocation(String name, String Location) {
        SqlSession sesssion = GetSession.getSesssion();
        AdminMapper mapper = sesssion.getMapper(AdminMapper.class);
        mapper.updateCourseLocation(name, Location);
        sesssion.commit();
        sesssion.close();
    }


    public void updateCourseStartTime(String name, Date startTime) {
        SqlSession sesssion = GetSession.getSesssion();
        AdminMapper mapper = sesssion.getMapper(AdminMapper.class);

        mapper.updateCourseStartTime(name, startTime);
        sesssion.commit();
        sesssion.close();
    }


}
