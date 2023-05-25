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
        name = null;
        x = 0;
        y = 0;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
