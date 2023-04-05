package VTime;


import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 模拟时间
 * @author maxiaotiao
 * @version 1.0
 */
public class VirtualTime {
    private static Calendar calendar = Calendar.getInstance();//用日历表示时间
    private static int rate = 10;//现实时间rate秒=虚拟时间1小时
    private Timer timer = new Timer();//定时器
    private int count = 1;//临时变量,统计执行次数，定时任务做好后可以

    /**
     * 目前的构造方法默认时间为2023.2.18，后续可能变成读取上次系统关闭时候的时间
     */
    public VirtualTime() {
        calendar.set(2023, 1, 18, 0, 0);
    }

    /**
     *返回当前月份
     * @return 返回当前月份
     */
    public static int getMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     *返回当前日期
     * @return 返回当前日期
     */
    public static int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     *返回当前时间（小时）
     * @return 返回当前时间（小时）
     */
    public static int getHours() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     *返回当前是星期几
     * @return 返回当前是星期几
     */
    public static int getDayOfWeek() {
        int DayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (DayOfWeek != 1) {
            return DayOfWeek - 1;
        } else return 7;
    }

    /**
     *返回当前时间倍速：x秒=1h
     * @return 返回当前时间倍速：x秒=1h
     */
    public int getRate() {
        return rate;
    }


    /**
     *
     * @return 返回当字符串格式的完整时间
     */
    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss EE");
        return sdf.format(calendar.getTime());

    }


    /**
     *定时器内容，后续放定时任务
     */
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {


            calendar.add(Calendar.HOUR_OF_DAY, 1);//时间推进

            //模拟执行任务区
            System.out.println(getTime() + "   执行任务，循环第" + count++ + "次");


        }
    }


    /**
     *开启定时器，参数为自定义时间
     * @param year 年份
     * @param month 月份
     * @param day 日期
     * @param hour 小时
     */
    public void TimeStart(int year, int month, int day, int hour) {
        calendar.set(year, month - 1, day, hour, 0, 0);
        timer.scheduleAtFixedRate(new MyTimerTask(), (long) rate * 1000, (long) rate * 1000);
    }

    /**
     * 开启定时器，空参构造表示以默认时间开启，后续可能升级为从数据库读取上次时间
     */
    public void TimeStart() {
        this.TimeStart(2023, 2, 18, 0);
    }

    /**
     * 暂停时钟
     * @throws InterruptedException
     */
    public void pause() throws InterruptedException {
        timer.cancel();
    }

    /**
     * 重启时钟
     */
    public void restart() {
        timer = new Timer();
        this.TimeStart(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY));
    }

    /**
     * 快进，一共三档速度，10s，5s，2s
     */
    public void FF() {
        switch (rate) {
            case 10:
                rate = 5;
                break;
            case 5:
                rate = 2;
                break;
            case 2:
                rate = 10;
                break;
            default:
                rate = 10;
                break;
        }

        timer.cancel();
        timer = new Timer();
        this.TimeStart(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY));
    }


}
