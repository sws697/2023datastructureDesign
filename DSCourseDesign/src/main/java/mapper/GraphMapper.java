package mapper;

import Graph.Node;
import Graph.Road;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface GraphMapper {
    ArrayList<Node>  getNodes();
    ArrayList<Road> getRoads();
    void addNode(@Param("name") String name,@Param("x") int x,@Param("y") int y);
    void addRoad(@Param("name1") String name1,@Param("name2") String name2,@Param("len") int len);
}
