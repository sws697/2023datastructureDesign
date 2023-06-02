package Users.tool;

public class StuForRec {
    String password;
    int sid;
    String location;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public StuForRec(String password, int sid, String location) {
        this.password = password;
        this.sid = sid;
        this.location = location;
    }

    @Override
    public String toString() {
        return "StuForRec{" +
                "password='" + password + '\'' +
                ", sid=" + sid +
                ", location='" + location + '\'' +
                '}';
    }
}
