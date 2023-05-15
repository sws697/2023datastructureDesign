package Users.tool;

import java.util.Date;

public class EventForRec {
    private int id;
    private String name;
    private String location;
    private Date startTime;
    private int tag;
    private int type;
    private int weekLast;
    private int hourLast;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getWeekLast() {
        return weekLast;
    }

    public void setWeekLast(int weekLast) {
        this.weekLast = weekLast;
    }

    public int getHourLast() {
        return hourLast;
    }

    public void setHourLast(int hourLast) {
        this.hourLast = hourLast;
    }

    @Override
    public String toString() {
        return "EventForRec{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", startTime=" + startTime +
                ", tag=" + tag +
                ", type=" + type +
                ", weekLast=" + weekLast +
                ", hourLast=" + hourLast +
                '}';
    }
}
