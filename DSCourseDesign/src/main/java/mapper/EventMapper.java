package mapper;

import TimeTable.Event;
import Users.tool.EventForRec;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface EventMapper {
    ArrayList<EventForRec> getEvents(@Param("student_id") int id);
}
