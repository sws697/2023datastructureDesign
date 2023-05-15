/**
 * 结点，代表建筑
 */
package Graph;

import java.awt.geom.Point2D;

public class Node {
    String name;//建筑名称
    int x;
    int y;
    public Node() {
        name = new String();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoint(int x,int y){
        this.x = x;
        this.y = y;
    }

}
