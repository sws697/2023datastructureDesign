package mapper;

import Users.Student;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface StudentMapper {

     Student select(@Param("id") int id, @Param("password") String password);
     void addExtra(@Param("name") String name, @Param("location") String location,
                   @Param("startTime") Date startTime, @Param("tag") int tag,
                   @Param("type") int type, @Param("weekLast") int weekLast,
                   @Param("hourLast") int hourLast, @Param("sid") int sid);
     void addTempo(@Param("name") String name,@Param("location") String location,
                   @Param("startTime") Date startTime,@Param("tag") int tag,
                   @Param("type") int type,@Param("weekLast") int weekLast,
                   @Param("hourLast") int hourLast,@Param("sid") int sid);
     void updateExtraLocation(@Param("name")String name,@Param("Location")String Location,@Param("sid") int sid);
     void updateTempoLocation(@Param("name")String name,@Param("Location")String Location,@Param("sid") int sid);
     void updateExtraStartTime(@Param("name")String name,@Param("startTime")Date startTime,@Param("sid") int sid);
     void updateTempoStartTime(@Param("name")String name,@Param("startTime")Date startTime,@Param("sid") int sid);
     void deleteTempo(@Param("name")String name,@Param("sid") int sid);
     void deleteExtra(@Param("name")String name,@Param("sid") int sid);
}
