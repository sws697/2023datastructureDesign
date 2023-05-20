package mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface AdminMapper {
    void addStu(@Param("sid") int sid,@Param("password")String password,@Param("location") String location);
    void addCourse(@Param("name") String name,@Param("location") String location,
                   @Param("startTime") Date startTime,@Param("tag") int tag,
                   @Param("type") int type,@Param("weekLast") int weekLast,
                   @Param("hourLast") int hourLast,@Param("sid") int sid,
                   @Param("link") String link);
    void removeCourse(@Param("name") String name,@Param("sid") int sid);
    void updateCourseLocation(@Param("name")String name,@Param("Location")String Location);
    void updateCourseStartTime(@Param("name")String name,@Param("startTime")Date startTime);}
