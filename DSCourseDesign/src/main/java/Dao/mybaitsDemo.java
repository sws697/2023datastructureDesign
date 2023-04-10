package Dao;

import Users.user_test;
import mapper.TestMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class mybaitsDemo {
    public static void main(String[] args) throws IOException {
        /**
         * 加载mybatisd的核心文件，获取sqlsessionFactory
         */
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //获取sqlsession对象，执行sql
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //执行
//        List<user_test> users = sqlSession.selectList("test.getUsers");
        TestMapper mapper = sqlSession.getMapper(TestMapper.class);
        List<user_test> user_tests = mapper.selectAll();

        System.out.println(user_tests);

        sqlSession.close();
    }
}
