package Users.tool;

import java.util.Date;

/**
 * @author maxiaotiao
 * 这个类用于接收日程，作为一个中介的作用，参数是所有的参数，每种日程根据自己需要的类获取
 */
public class EventForRec {
    private int id;
    private String name;
    private String location;
    private Date startTime;
    private int tag;

    /**
     *课程type = 1 ，课外活动type = 2， 临时事务type  = 3
     */
    private int type;
    private int weekLast;
    private int hourLast;
    private String link;

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
                ", link='" + link + '\'' +
                '}';
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

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


}
