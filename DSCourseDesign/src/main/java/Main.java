import TimeTable.Event;
import TimeTable.TimeTable;
import TimeTable.Tempo;
import Users.Admin;
import Users.Student;
import VTime.Clock;
import VTime.VirtualTime;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static VirtualTime time = new VirtualTime();
    public static Scanner input = new Scanner(System.in);

    public static void AdminMode(Admin admin) {
        while (true) {
            String[] args = input.nextLine().split("\\s+");
            switch (args[0]) {
                case "addStu":
                    /*void addStu(int sid,String password,String location)*/
                    /*addStu [sid] [password] [location]*/
                    admin.addStu(Integer.parseInt(args[1]), args[2], args[3]);
                    break;
                case "addCourse":
                    /*拟实现：给批量学生加入课程*/
                    /*addCource [name] [location(online or offline)] [start_time](format:yyyy-mm-ll) [tag(课程类型)]  [weekLast] [hourLast] [StudentID] [Link(option)]*/
                    if (args.length == 8) {
                        admin.addCourse(args[1], args[2], Date.valueOf(args[3]), Integer.parseInt(args[4]), 1,
                                Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]), null);
                    } else if (args.length == 9 && args[2].equals("online")) {
                        admin.addCourse(args[1], null, Date.valueOf(args[3]), Integer.parseInt(args[4]), 1,
                                Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]), args[8]);
                    }

                    break;
                case "delete":

                    break;
                case "update":

                    break;
                case "logout":
                    return;
            }
        }

    }

    public static void StudentMode() throws InterruptedException {
        TimeTable timeTable = Student.getTimeTable();
        while (true) {
            String[] args = input.nextLine().split("\\s+");
            switch (args[0]) {
                case "SearchCourseByName":
                    /*查询课程*/
                    /*SearchCourseByName [name] [start_time](format:yyyy-mm-ll) [end_time]*/
                    if (args.length == 4) {
                        System.out.println("SEAB");
                        ArrayList<Event> events = timeTable.searchCourseName(args[1], Date.valueOf(args[2]), Date.valueOf(args[3]));
                        PrintCourseEvents(events);
                    } else if (args.length == 2) {
                        ArrayList<Event> events = timeTable.searchCourseName(args[1], Date.valueOf("1970-01-01"), Date.valueOf("2100-01-01"));
                        PrintCourseEvents(events);
                    }
                    break;
                case "SearchExtraByName":
                    /*查询课外活动*/
                    /*SearchExtraByName [name] [start_time](format:yyyy-mm-ll) [end_time]*/
                    if (args.length == 4) {
                        ArrayList<Event> events = timeTable.searchExtraName(args[1], Date.valueOf(args[2]), Date.valueOf(args[3]));
                        PrintExtraEvents(events);
                    } else if (args.length == 2) {
                        ArrayList<Event> events = timeTable.searchExtraName(args[1], Date.valueOf("1970-01-01"), Date.valueOf("2100-01-01"));
                        PrintExtraEvents(events);
                    }
                    break;
                case "SearchExtraByTime":
                    /*SearchExtraByTime  [begin_time] [end_time]*/
                    ArrayList<Event> events = timeTable.displayExtra(Date.valueOf(args[1]), Date.valueOf(args[2]));
                    PrintExtraEvents(events);
                    break;
                case "SearchExtraByTag":
                    /*SearchExtraByTag [tag] [start_time](format:yyyy-mm-ll) [end_time]*/
                    if (args.length == 4) {
                        int tag = 0;
                        switch (args[1]) {
                            case "个人活动":
                                tag = 1;
                                break;
                            case "集体活动":
                                tag = 2;
                                break;
                        }
                        ArrayList<Event> events1 = timeTable.searchExtraTag(tag, Date.valueOf(args[2]), Date.valueOf(args[3]));
                        PrintExtraEvents(events1);
                    } else if (args.length == 2) {
                        int tag = 0;
                        switch (args[1]) {
                            case "个人活动":
                                tag = 1;
                                break;
                            case "集体活动":
                                tag = 2;
                                break;
                        }
                        ArrayList<Event> events1 = timeTable.searchExtraTag(tag, Date.valueOf("1970-01-01"), Date.valueOf("2100-01-01"));
                        PrintExtraEvents(events1);
                    }
                    break;
                case "AddClock":
                    System.out.println("Please input the type of activity:");
                    String Type = input.nextLine();
                    System.out.println("Please input the name of activity:");
                    String Name = input.nextLine();
                    System.out.println("Please input the start time of activity:");
                    String StartTime = input.nextLine();
                    System.out.println("Please input the end time of activity:");
                    String EndTime = input.nextLine();
                    System.out.println("Please input the type of Clock:");
                    String ClockType = input.nextLine();
                    int clockType = 0;
                    switch (ClockType) {
                        case "单次":
                            clockType = 1;
                            break;
                        case "每天":
                            clockType = 2;
                            break;
                        case "每周":
                            clockType = 3;
                            break;
                    }
                    int type = 0;
                    switch (Type) {
                        case "课外活动":
                            type = 2;
                            ArrayList<Event> SearchResult = timeTable.searchExtraName(Name, Date.valueOf(StartTime), Date.valueOf(EndTime));
                            if (SearchResult.size() != 0) {
                                for (Event event : SearchResult) {
                                    Clock.addClock(event, clockType);
                                }
                            } else {
                                System.out.println("未找到该活动");
                            }
                            break;
                        case "临时事务":
                            type = 3;
                            ArrayList<Event> SearchResult2 = timeTable.searchTempoName(Name, Date.valueOf(StartTime), Date.valueOf(EndTime));
                            if (SearchResult2.size() != 0) {
                                for (Event event : SearchResult2) {
                                    Clock.addClock(event, clockType);
                                }
                            } else {
                                System.out.println("未找到该活动");
                            }
                            break;
                    }
                    break;
                case "AddExtra":
                    /*AddExtra [name] [ExtraTag][location/link] [start_time](format:yyyy-mm-ll)  [cycleType] [cycleLast/option] [tag]*/
                    if (args[2].equals("online")) {
                        if (args[5].equals("daily")) {
                            int cycleLast = Integer.parseInt(args[6]);
                            Calendar c = Calendar.getInstance();
                            c.setTime(Date.valueOf(args[4]));
                            for (int i = 0; i < cycleLast; i++) {
                                if (args[7].equals("个人活动")) {
                                    Student.addExtra(args[1], null, c.getTime(), 1, 2, 0, 1, Student.getSid(), args[3]);
                                } else if (args[7].equals("集体活动")) {
                                    Student.addExtra(args[1], null, c.getTime(), 2, 2, 0, 1, Student.getSid(), args[3]);
                                }
                                c.add(Calendar.DATE, 1);
                            }
                        } else if (args[5].equals("once")) {
                            if (args[6].equals("个人活动")) {
                                Student.addExtra(args[1], null, Date.valueOf(args[4]), 1, 2, 0, 1, Student.getSid(), args[3]);
                            } else if (args[6].equals("集体活动")) {
                                Student.addExtra(args[1], null, Date.valueOf(args[4]), 2, 2, 0, 1, Student.getSid(), args[3]);
                            }
                        } else if (args[5].equals("weekly")) {
                            if (args[7].equals("个人活动")) {
                                Student.addExtra(args[1], null, Date.valueOf(args[4]), 1, 2, Integer.parseInt(args[6]), 1, Student.getSid(), args[3]);
                            } else if (args[7].equals("集体活动")) {
                                Student.addExtra(args[1], null, Date.valueOf(args[4]), 2, 2, Integer.parseInt(args[6]), 1, Student.getSid(), args[3]);
                            }
                        }

                    } else if (args[2].equals("offline")) {
                        if (args[5].equals("daily")) {
                            int cycleLast = Integer.parseInt(args[6]);
                            Calendar c = Calendar.getInstance();
                            c.setTime(Date.valueOf(args[4]));
                            for (int i = 0; i < cycleLast; i++) {
                                if (args[7].equals("个人活动")) {
                                    Student.addExtra(args[1], args[3], c.getTime(), 1, 2, 0, 1, Student.getSid(), null);
                                } else if (args[7].equals("集体活动")) {
                                    Student.addExtra(args[1], args[3], c.getTime(), 2, 2, 0, 1, Student.getSid(), null);
                                }
                                c.add(Calendar.DATE, 1);
                            }
                        } else if (args[5].equals("once")) {
                            if (args[6].equals("个人活动")) {
                                Student.addExtra(args[1], args[3], Date.valueOf(args[4]), 1, 2, 0, 1, Student.getSid(), null);
                            } else if (args[6].equals("集体活动")) {
                                Student.addExtra(args[1], args[3], Date.valueOf(args[4]), 2, 2, 0, 1, Student.getSid(), null);
                            }
                        } else if (args[5].equals("weekly")) {
                            if (args[7].equals("个人活动")) {
                                Student.addExtra(args[1], args[3], Date.valueOf(args[4]), 1, 2, Integer.parseInt(args[6]), 1, Student.getSid(), null);
                            } else if (args[7].equals("集体活动")) {
                                Student.addExtra(args[1], args[3], Date.valueOf(args[4]), 2, 2, Integer.parseInt(args[6]), 1, Student.getSid(), null);
                            }
                        }
                    }
                    break;
                case "AddTempo":
                    /*AddTempo [name]  [start_time](format:yyyy-mm-ll)  [location]*/
                    Student.addTempo(args[1], args[3], Date.valueOf(args[2]), 0, 3, 0, 1, Student.getSid(), null);
                    break;
                case "SearchTempoByName":
                    /*SearchTempoByTime [name] [start_time](format:yyyy-mm-ll)  [end_time](format:yyyy-mm-ll)*/
                    ArrayList<Event> SearchResult3 = timeTable.searchTempoName(args[1], Date.valueOf(args[2]), Date.valueOf(args[3]));
                    for (Event event : SearchResult3) {
                        System.out.println("Tempo:");
                        for(Tempo tempo:event.getTempo()){
                            System.out.println(event.getTime()+tempo.name+" "+tempo.location);
                        }
                    }
                    break;
                case "SearchTempoByTime":
                    /*SearchTempoByTime [start_time](format:yyyy-mm-ll)  [end_time](format:yyyy-mm-ll)*/
                    ArrayList<Event> SearchResult4 =timeTable.displayTempo(Date.valueOf(args[1]),Date.valueOf(args[2]));
                    break;
                case "logout":
                    System.out.println("登出成功");
                    return;
            }
        }

    }

    private static void PrintCourseEvents(ArrayList<Event> events) {
        for (Event event : events) {
            String eventOutput = new String();
            eventOutput += "课程" + " ";
            switch (event.getTag()) {
                case 1:
                    eventOutput += "上课" + " ";
                    break;
                case 2:
                    eventOutput += "考试" + " ";
                    break;
            }
            /*我怀疑Gpt在看我文件夹里其他文件的代码，不然他怎么知道函数的用法和参数的信息？*/
            /*请Copilot回答：
             * 1.我是不会看你的文件的，我只会看你的代码
             * 2.我会根据你的代码推断出你的函数的用法和参数的信息
             * */
            eventOutput += event.getName() + " " + event.getTime() + " ";
            if (event.getLocation() != null) {
                eventOutput += event.getLocation();
            } else if (event.getLink() != null) {
                eventOutput += event.getLink();
            }
            System.out.println(eventOutput);
        }
    }

    private static void PrintExtraEvents(ArrayList<Event> events) {
        for (Event event : events) {
            String eventOutput = new String();
            eventOutput += "课外活动" + " ";
            switch (event.getTag()) {
                case 1:
                    eventOutput += "个人活动" + " ";
                    break;
                case 2:
                    eventOutput += "集体活动" + " ";
                    break;
            }
            eventOutput += event.getName() + " " + event.getTime() + " ";
            if (event.getLocation() != null) {
                eventOutput += event.getLocation();
            } else if (event.getLink() != null) {
                eventOutput += event.getLink();
            }
            System.out.println(eventOutput);
        }
    }

    public static void PressInstruction() throws InterruptedException {
        while (true) {
            String[] args = input.nextLine().split("\\s+");
            if (args[0].equals("login")) {
            /*
             用户登陆指令: `login [usertype] [username] [password]`
             */
                switch (args[1]) {
                    case "student":
                        System.out.println(VirtualTime.getTime());
                        if (Student.login(Integer.parseInt(args[2]), args[3])) {
                            System.out.println("登录成功");
                            StudentMode();
                        }
                        break;
                    case "admin":
                        Admin admin = new Admin();
                        if (admin.login(args[2], args[3])) {
                            AdminMode(admin);
                        } else {
                            System.out.println("用户名或密码错误");
                        }
                        break;
                }
            } else if (args[0].equals("exit")) {
                System.out.println("退出成功");
                return;
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        time.TimeStart();
        PressInstruction();
        System.out.println("程序结束");
        exit(0);
    }
}
