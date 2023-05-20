package mapper;

import Users.Student;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface StudentMapper {

     Student select(@Param("id") int id, @Param("password") String password);
     void addExtra(@Param("name") String name, @Param("location") String location,
                   @Param("startTime") Date startTime, @Param("tag") int tag,
                   @Param("type") int type, @Param("weekLast") int weekLast,
                   @Param("hourLast") int hourLast, @Param("sid") int sid,
                   @Param("link") String link);
     void addTempo(@Param("name") String name,@Param("location") String location,
                   @Param("startTime") Date startTime,@Param("tag") int tag,
                   @Param("type") int type,@Param("weekLast") int weekLast,
                   @Param("hourLast") int hourLast,@Param("sid") int sid,
                   @Param("link") String link);
     void updateExtra(@Param("startTime") Date startTime,@Param("name")String newName,@Param("Location")String newLocation,
                              @Param("sid") int sid,@Param("tag") int newTag,@Param("link") String newLink);
     void updateTempo(@Param("startTime") Date startTime,@Param("oldname")String oldName,@Param("newName") String newName,@Param("Location")String newLocation,
                      @Param("sid") int sid,@Param("tag") int newTag,@Param("link") String newLink);

     void deleteTempo(@Param("name")String name,@Param("sid") int sid);
     void deleteExtra(@Param("name")String name,@Param("sid") int sid);
}
