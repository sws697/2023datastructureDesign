package VTime;

import TimeTable.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * 主要用于课外活动和临时事务闹钟
 */
public class Clock {
    /**
     * 种类，2表示课外活动，3表示临时事务
     */
    private int type;
    private Date startTime;
    /**
     * 周期，1表示单次，2表示每天一次，3表示每周一次
     * 前提是这个活动也是每周都有，或者每天都有，不能凭空出现
     */
    private int period;



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Clock(int type, Date startTime, int period) {
        this.type = type;
        this.startTime = startTime;
        this.period = period;

    }

    /**
     * 存储当前用户的闹钟，目前的设定是运行时设置，后续可能添加持久化
     */
    public static HashMap<Long,Clock> Clocks = new HashMap<>();


    /**
     * 查询当前时段是否有要提醒的日程，返回boolean类型,查询到之后将当前闹钟从表中删除，如果是周期性，则重新添加
     */
    public static boolean Ring(){
        long millSecond =  VirtualTime.getTime().getTime();
        Clock clock = Clocks.get(millSecond);
        if(clock == null) return false;

        else{
            Clocks.remove(millSecond);
            if(clock.getType()==2) {
                    if(clock.getPeriod()==2){
                        Date date = new Date(millSecond+(long)86400000);
                        clock.setStartTime(date);
                        Clocks.put(millSecond+(long)86400000,clock);
                    }else if (clock.getPeriod()==3){
                        Date date = new Date(millSecond+(long)604800000);
                        clock.setStartTime(date);
                        Clocks.put(millSecond+(long)604800000,clock);
                    }
            }
            return true;
        }
    }/*这个Ring函数好像如果设置Clock为周期那么他会无限延续下去。那如果我要设置指定一段时间的周期闹钟怎么办？*/

    public static void addClock(Event event,int period){
        switch (event.getType()){
            case 2:
                Clock clock = new Clock(2,event.getTime(),period);
                Clock.Clocks.put(event.getTime().getTime(),clock);
                break;
            case 3:
                Clock clock1 = new Clock(3,event.getTime(),1);
                Clock.Clocks.put(event.getTime().getTime(),clock1);
                break;
        }
    }
}
