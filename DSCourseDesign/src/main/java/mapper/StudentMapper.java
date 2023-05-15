package mapper;

import Users.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentMapper {

     Student select(@Param("id") int id, @Param("password") String password);
}
