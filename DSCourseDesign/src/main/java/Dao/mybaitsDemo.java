package Dao;

import Users.Student;
import mapper.StudentMapper;
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
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//
//        //获取sqlsession对象，执行sql
//        SqlSession sqlSession = sqlSessionFactory.openSession();

        //执行


        SqlSession sesssion = GetSession.getSesssion();
        StudentMapper mapper =sesssion.getMapper(StudentMapper.class);
        Student select = mapper.select(2021211114, "12345");
        System.out.println(select);


        sesssion.close();
    }
}
