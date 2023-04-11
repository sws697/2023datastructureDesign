package Users;

import Dao.GetSession;
import mapper.StudentMapper;
import org.apache.ibatis.session.SqlSession;

/**
 * 学生类，提供登录，查询等接口
 * @author maxiaotiao
 */
public class Student {
    /**
     * 为0表示当前未登录成功
     */
    private static long id=0L;
    private static String password="0";

    public static long getId() {
        return Student.id;
    }

    public void setId(long id) {
        Student.id = id;
    }

    public static String getPassword() {
        return password;
    }

    public static void  setPassword(String password) {
        Student.password = password;
    }

    /**
     * 用户登录
     * @param id 学号
     * @param password 密码
     * @return 登录成功返回true
     */
    public static boolean login(long id,String password){
        SqlSession sesssion = GetSession.getSesssion();
        StudentMapper mapper = sesssion.getMapper(StudentMapper.class);
            Student student = mapper.select(id, password);
            sesssion.close();
            if (student!=null){
                Student.id=id;
                Student.password=password;
                return true;
        }
        return false;
    }


}
