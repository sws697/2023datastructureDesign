package Graph;

public class Road {
    String name1;
    String name2;
    int len;

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public Road(String name1, String name2, int len) {
        this.name1 = name1;
        this.name2 = name2;
        this.len = len;
    }
}
