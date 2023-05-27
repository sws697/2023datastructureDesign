package Users;

import Dao.GetSession;
import mapper.AdminMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;

/**
 * 管理员类，目前未具体实现
 * @author maxiaotiao
 */
public class Admin {
    private String username="admin";
    private String password="admin";

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

    public boolean login(String username,String password){
        if(this.username.equals(username)&this.password.equals(password)){
            return true;
        }
        else return false;
    }

    public void addStu(int sid,String password,String location){
        SqlSession session = GetSession.getSesssion();
        AdminMapper mapper  = session.getMapper(AdminMapper.class);
        mapper.addStu(sid,password,location);
        session.commit();
        session.close();
    }

    public void addCourse(String name, String lcoation,
                          Date startTime,int tag,
                          int type,int weekLast,int hourLast,
                          int sid,String link){
        SqlSession session = GetSession.getSesssion();
        AdminMapper mapper = session.getMapper(AdminMapper.class);
        mapper.addCourse(name,lcoation,startTime,tag,type,weekLast,hourLast,sid,link);
        session.commit();
        session.close();
    }

    public void removeCourse(String name,int sid){
        SqlSession session = GetSession.getSesssion();
        AdminMapper mapper = session.getMapper(AdminMapper.class);
        mapper.removeCourse(name,sid);
        session.commit();
        session.close();
    }

    public void updateCourseLocation(String name,String Location){
        SqlSession sesssion = GetSession.getSesssion();
        AdminMapper mapper = sesssion.getMapper(AdminMapper.class);
        mapper.updateCourseLocation(name,Location);
        sesssion.commit();
        sesssion.close();
    }

    public void updateCourseStartTime(String name, Date startTime){
        SqlSession sesssion = GetSession.getSesssion();
        AdminMapper mapper = sesssion.getMapper(AdminMapper.class);
        mapper.updateCourseStartTime(name,startTime);
        sesssion.commit();
        sesssion.close();
    }








}
