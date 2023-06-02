package VTime;


import GUI.Navigate2;
import Graph.Graph;
import Graph.Node;
import TimeTable.Event;
import TimeTable.Tempo;
import Users.Student;


import java.io.*;
import java.util.*;

/**
 * 模拟时间
 *
 * @author maxiaotiao
 * @version 1.0
 */
public class VirtualTime {
    private static  Calendar calendar = Calendar.getInstance();//用日历表示时间
    private static int rate = 2;//现实时间rate秒=虚拟时间1小时
    private Timer timer = new Timer();//定时器
    private int count = 1;//临时变量,统计执行次数，定时任务做好后可以
    private static File log = new File("log.txt");
    private static PrintWriter pw;
    boolean isDo = false;

    public static Calendar getCalendar() {
        Calendar newCalendar  = (Calendar) calendar.clone();
        newCalendar.set(Calendar.MINUTE,0);
        newCalendar.set(Calendar.SECOND,0);
        newCalendar.set(Calendar.MILLISECOND,0);

        return newCalendar;
    }


    /**
     * 目前的构造方法默认时间为2023.2.18，后续可能变成读取上次系统关闭时候的时间
     */
    public VirtualTime() {
        calendar.set(2023, Calendar.FEBRUARY, 18, 0, 0,0);
    }

    /**
     * 返回当前月份
     *
     * @return 返回当前月份
     */
    public static int getMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回当前日期
     *
     * @return 返回当前日期
     */
    public static int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回当前时间（小时）
     *
     * @return 返回当前时间（小时）
     */
    public static int getHours() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回当前是星期几
     *
     * @return 返回当前是星期几
     */
    public static int getDayOfWeek() {
        int DayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (DayOfWeek != 1) {
            return DayOfWeek - 1;
        } else return 7;
    }

    /**
     * 返回当前时间倍速：x秒=1h
     *
     * @return 返回当前时间倍速：x秒=1h
     */
    public static int getRate() {
        return rate;
    }


    /**
     * @return 返回当前时间的Date对象
     */
    public static Date getTime() {
        Calendar newCalendar  = (Calendar) calendar.clone();
        newCalendar.set(Calendar.MINUTE,0);
        newCalendar.set(Calendar.SECOND,0);
        newCalendar.set(Calendar.MILLISECOND,0);
        return newCalendar.getTime();

    }


    /**
     * 定时器内容，后续放定时任务
     */
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {


            calendar.add(Calendar.HOUR_OF_DAY, 1);//时间推进

            //模拟执行任务区

            HourlyTask();

//            System.out.println(getTime() + "   执行任务，循环第" + count++ + "次");


        }
    }

    /**
     * 开启定时器，参数为自定义时间
     *
     * @param year  年份
     * @param month 月份
     * @param day   日期
     * @param hour  小时
     */
    public void TimeStart(int year, int month, int day, int hour) throws FileNotFoundException {
        calendar.set(year, month - 1, day, hour, 0, 0);
        OutputStream out = new FileOutputStream(log, true);
        pw = new PrintWriter(out);
        timer.scheduleAtFixedRate(new MyTimerTask(), (long) rate * 1000, (long) rate * 1000);
    }

    /**
     * 开启定时器，空参构造表示以默认时间开启，后续可能升级为从数据库读取上次时间
     */
    public void TimeStart() throws FileNotFoundException {
        OutputStream out = new FileOutputStream(log, true);
        pw = new PrintWriter(out);
        this.TimeStart(2023, 2, 18, 0);
    }

    /**
     * 暂停时钟
     */
    public void pause() throws InterruptedException {
        timer.cancel();
    }

    /**
     * 重启时钟
     */
    public void restart() throws FileNotFoundException {
        timer = new Timer();
        this.TimeStart(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY));
    }

    /**
     * 快进，一共三档速度，10s，5s，2s
     */
    public void FF() throws FileNotFoundException {
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
        this.TimeStart(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY));
    }


    /**
     * 每个小时都要执行一次任务体，现在主要是课程提醒和闹钟
     * 调用导航方面，途径最短路还没改过来，而且我需要一个能够直接调用的方法，导航功能需要在做一层封装，所以暂时不做途径最短路
     * 这只是第一个版本，可能根据需求再改
     */
    public void HourlyTask() {
        if(getHours()==0){
            System.out.println(getTime());
            pw.println(getTime());
        }
        Event nextEvent = Student.CourseAdvanceRemind();
        if (nextEvent != null) {
            System.out.println(getTime());
            Event currentEvent = Student.getCurrentEvent();
            if (currentEvent != null) {
                if (currentEvent.getName().equals(nextEvent.getName())) {
                    System.out.println("同一门课的不同时间段");
                } else {
                    System.out.println("下一门课是" + nextEvent.getName());
                    pw.println("下一门课是" + nextEvent.getName());
                    if(nextEvent.getLocation()!=null){
                        Graph graph = Student.getGraph();
                        ArrayList<Node> path = graph.shortestPath(Student.getLocation(), nextEvent.getLocation());
                        for(Node node:path){
                            System.out.print(node.getName()+"->");
                            pw.print(node.getName()+"->");
                        }
                        Navigate2 navigate2 = new Navigate2(path);
//                        NavigationGUI navigationGUI = new NavigationGUI(path);
                    }else if (nextEvent.getLink()!=null) {
                        String url = nextEvent.getLink();
                        String[] temp= url.split(".");
                        System.out.println("课程平台为"+temp[1]);
                        System.out.println("课程链接为" + nextEvent.getLink());
                        pw.println("课程平台为"+temp[1]);
                        pw.println("课程链接为" + nextEvent.getLink());
                    }
                }
            } else {
                System.out.println("下一门课是" + nextEvent.getName());
                pw.println("下一门课是" + nextEvent.getName());
                if(nextEvent.getLocation()!=null){
                    Graph graph = Student.getGraph();
                    ArrayList<Node> path = graph.shortestPath(Student.getLocation(), nextEvent.getLocation());
                    for(int i=0;i<path.size()-1;i++){
                        System.out.print(path.get(i).getName()+"->");
                        pw.print(path.get(i).getName()+"->");
                    }
                    System.out.println(path.get(path.size()-1).getName());
                    Navigate2 navigate2 = new Navigate2(path);
//                    NavigationGUI navigationGUI = new NavigationGUI(path);
                } else if (nextEvent.getLink()!=null) {
                    String url = nextEvent.getLink();
                    String[] temp= url.split(".");
                   System.out.println("课程平台为"+temp[1]);
                   pw.println("课程平台为"+temp[1]);
                    System.out.println("课程链接为" + nextEvent.getLink());
                    pw.println("课程链接为" + nextEvent.getLink());
                }
            }
        }

        if (Clock.Ring()) {
            Calendar calendar = VirtualTime.getCalendar();
            System.out.println(calendar.getTime()+"闹钟响了");
            calendar.set(Calendar.HOUR_OF_DAY, VirtualTime.getHours());
            Date startTime = calendar.getTime();
            calendar.set(Calendar.HOUR_OF_DAY, VirtualTime.getHours() + 1);
            Date endTime = calendar.getTime();
            ArrayList<Event> events = Student.getTimeTable().displayExtra(startTime, endTime);
            ArrayList<Event> events1 =Student.getTimeTable().displayTempo(startTime,endTime);
            if (events.size()!=0) {
                Event event = events.get(0);
                System.out.println(calendar.getTime()+"当前课外活动为: " + event.getName());
                if(event.getLocation()!=null) {
                    Graph graph = Student.getGraph();
                    ArrayList<Node> path = graph.shortestPath(Student.getLocation(), event.getLocation());
                    for(Node node:path){
                        System.out.print(node.getName()+"->");
                        pw.print(node.getName()+"->");
                    }
//                    NavigationGUI navigationGUI = new NavigationGUI(path);
                }else if (event.getLink()!=null) {
                    String url = event.getLink();
                    String[] temp= url.split(".");
                    System.out.println("线上平台为"+temp[1]);
                    System.out.println("线上活动链接为" + event.getLink());
                }
            } else if (events1.size()!=0) {
                Event event = events1.get(0);
                System.out.println(calendar.getTime()+" 当前临时事务:");
                pw.println(calendar.getTime()+" 当前临时事务:");
                ArrayList<Tempo> tempos = event.getTempo();
                ArrayList<String> tempoLocations = new ArrayList<>();
                for(Tempo tempo:tempos){
                    tempoLocations.add(tempo.location);
                    System.out.println(tempo.name+" "+tempo.location);
                    pw.println(tempo.name+" "+tempo.location);
                }
                Graph graph = Student.getGraph();
                ArrayList<Node> path = graph.pathByTheWay(Student.getLocation(), tempoLocations);
                for(int i=0;i<path.size()-1;i++){
                    System.out.print(path.get(i).getName()+"->");
                    pw.print(path.get(i).getName()+"->");
                }
                System.out.println(path.get(path.size()-1).getName());
                pw.println(path.get(path.size()-1).getName());
//                NavigationGUI navigationGUI = new NavigationGUI(path);
            }
        }


        if (VirtualTime.getHours() == 22) {
            ArrayList<Event> events = Student.DailyExtraRemind();
            System.out.println("明天的课外活动为：");
            for(Event event:events){
                System.out.println(event.getTime()+event.getName());
            }
        }

        if (VirtualTime.getHours() == 23) {
            ArrayList<Event> events = Student.DailyCourseRemind();
            System.out.println(events.size());
            System.out.println("明天的课程为：");
            for(Event event:events){
                System.out.println(event.getTime()+event.getName());
            }
        }
    }
}
