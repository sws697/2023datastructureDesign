package Dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 工具类：获取会话
 * @author maxiaotiao
 */
public class GetSession {
    private static final String resource = "mybatis-config.xml";
    private static InputStream inputStream;

    static {
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    /**
     *
     * @return返回一个sql会话对象
     */
    public static SqlSession getSesssion(){
        return sqlSessionFactory.openSession();
    }









    public GetSession() throws IOException {
    }
}
