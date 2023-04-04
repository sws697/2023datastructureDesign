package VTime;


import java.text.SimpleDateFormat;
import java.util.*;

public class VirtualTime {
    private  static Calendar calendar = Calendar.getInstance();//用日历表示时间
    private  int rate = 10;//现实时间rate秒=虚拟时间1小时
    private Timer timer = new Timer();//定时器
    int count=1;//临时变量,统计执行次数，定时任务做好后可以

    //构造函数
    public VirtualTime() {
        calendar.set(2023,1,18,0,0);
    }

    //返回时间的各个参数
    public  static int getMonth() {
        return calendar.get(Calendar.MONTH)+1;
    }
    public  static int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    public  static int getHours() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    public  static int getDayOfWeek() {
        int DayOfWeek=calendar.get(Calendar.DAY_OF_WEEK);
        if(DayOfWeek!=1){
            return DayOfWeek-1;
        }else return 7;
    }

    //返回当前倍速
    public  int getRate() {
        return rate;
    }



    //返回的是字符串格式的时间
    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss EE");
        return sdf.format(calendar.getTime());

    }


    //定时器内容
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {


            calendar.add(Calendar.HOUR_OF_DAY, 1);//时间推进

            //模拟执行任务区
            System.out.println(getTime() + "   执行任务，循环第" + count++ + "次");


        }
    }



    //开启时间,时间获取方式待定，可以手动输入也可也继承上次的时间，默认时间为2023年2月18日0时0分0秒
    public void TimeStart(int year,int month,int day,int hour) {
        calendar.set(year,month-1,day,hour,0,0);
        timer.scheduleAtFixedRate(new MyTimerTask(),(long)rate*1000,(long)rate*1000);
    }

    public void TimeStart(){
        this.TimeStart(2023,2,18,0);
    }

    public void pause() throws InterruptedException {
        timer.cancel();
    }

    public void restart(){
        timer = new Timer();
        this.TimeStart(calendar.getTime().getYear(),calendar.getTime().getMonth(),calendar.getTime().getDay(),
                calendar.getTime().getHours());
    }

    public void FF(){
        switch(rate){
            case 10:rate=5;break;
            case 5:rate=2;break;
            case 2:rate=10;break;
            default:rate=10;break;
        }

        timer.cancel();
        timer = new Timer();
        this.TimeStart(calendar.getTime().getYear(),calendar.getTime().getMonth(),calendar.getTime().getDay(),
                calendar.getTime().getHours());
    }




}
