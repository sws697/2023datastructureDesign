/**
 * 边，代表道路
 */
package Graph;

public class Edge {
    int next;//下一条边
    int to;//终点
    int len;//边的长度
    String name;//道路名称

    public Edge() {
        next = 0;
        to = 0;
        len = 0;
        name = new String();
    }
}
