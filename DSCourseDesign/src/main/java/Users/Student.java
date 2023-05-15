package Users;

import Dao.GetSession;
import Users.tool.EventForRec;
import mapper.EventMapper;
import mapper.StudentMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;

/**
 * 学生类，提供登录，查询等接口
 * @author maxiaotiao
 */
public class Student {
    /**
     * 为0表示当前未登录成功
     */
    private static int sid =0;
    private static String password="0";
    /**
     * 目前暂定学生初始位置为学五
     */
   private static int x;
   private static int y;

    /**
     *
     * @return 返回当前系统用户Id（学号）
     */
    public static long getSid() {
        return Student.sid;
    }

    public void setId(int id) {
        Student.sid = id;
    }

    public static String getPassword() {
        return password;
    }

    public static void  setPassword(String password) {
        Student.password = password;
    }

    public static int getX() {
        return x;
    }

    public static void setX(int x) {
        Student.x = x;
    }

    public static int getY() {
        return y;
    }

    public static void setY(int y) {
        Student.y = y;
    }

    public static void setLocation(int x,int y){
        Student.x = x;
        Student.y = y;
    }

    /**
     * 用户登录
     * @param id 学号
     * @param password 密码
     * @return 登录成功返回true
     */
    public static boolean login(int id,String password){
        SqlSession sesssion = GetSession.getSesssion();
        StudentMapper mapper = sesssion.getMapper(StudentMapper.class);
            Student student = mapper.select(id, password);
            sesssion.close();
            if (student!=null){
                Student.sid =id;
                Student.password=password;
                Student.setLocation(102,198);
                return true;
        }
        return false;
    }

    public static ArrayList<EventForRec> getEvents(int id){
        SqlSession session = GetSession.getSesssion();
        EventMapper mapper = session.getMapper(EventMapper.class);
        ArrayList<EventForRec> events = mapper.getEvents(id);
        session.close();
        return events;
    }




}
