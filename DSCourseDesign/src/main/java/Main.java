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
                case "addCourseOneByOne":
                    /*拟实现：给单独学生加入课程*/
                    /*addCourceOneByOne [name] [location(online or offline)] [start_time](format:yyyy-mm-ll) [tag(课程类型)]  [weekLast] [hourLast] [StudentID] [Link(option)]*/
                    if (args.length == 8) {
                        admin.addCourse(args[1], args[2], Date.valueOf(args[3]), Integer.parseInt(args[4]), 1,
                                Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]), null);
                    } else if (args.length == 9 && args[2].equals("online")) {
                        admin.addCourse(args[1], null, Date.valueOf(args[3]), Integer.parseInt(args[4]), 1,
                                Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]), args[8]);
                    }
                    break;
                case "deleteCourseOneByOne":
                    System.out.println("Please enter the Course you want to delete");
                    String name2 = input.nextLine();
                    System.out.println("Please enter the related StudentID");
                    String sid = input.nextLine();
                    admin.removeCourse(name2, Integer.parseInt(sid));
                    break;
                case "MassAddCourse":
                    System.out.println("Please enter the Course you want to add");
                    String name3 = input.nextLine();
                    System.out.println("Please enter the range of StudentID(请一行一行输入学生id)");
                    int sid1 = Integer.parseInt(input.nextLine());
                    int sid2 = Integer.parseInt(input.nextLine());
                    System.out.println("Please enter the start time");
                    String startTime = input.nextLine();
                    System.out.println("Please enter the hourLast");
                    int hourLast = Integer.parseInt(input.nextLine());
                    System.out.println("Please enter the tag of the course:上课 or 考试");
                    String Tag = input.nextLine();
                    int tag = 1;
                    int weeklast = 1;
                    if (Tag.equals("上课")) {
                        tag = 1;
                        System.out.println("Please enter the weekLast");
                        weeklast = Integer.parseInt(input.nextLine());
                    } else if (Tag.equals("考试")) {
                        tag = 2;
                        weeklast = 1;
                    }
                    System.out.println("Please enter the type of the course:online or offline");
                    String type = input.nextLine();
                    String link = null, location1 = null;
                    if (type.equals("online")) {
                        System.out.println("Please enter the link");
                        link = input.nextLine();
                        location1 = null;
                    } else if (type.equals("offline")) {
                        System.out.println("Please enter the location");
                        link = null;
                        location1 = input.nextLine();
                    }
                    for (int i = sid1; i <= sid2; i++) {
                        admin.addCourse(name3, location1, Date.valueOf(startTime), tag, 1, weeklast, hourLast, i, link);
                    }
                    break;
                case "MassDeleteCourse":
                    System.out.println("Please enter the Course you want to delete");
                    String name4 = input.nextLine();
                    System.out.println("Please enter the range of StudentID");
                    int sid3 = Integer.parseInt(input.nextLine());
                    int sid4 = Integer.parseInt(input.nextLine());
                    for (int i = sid3; i <= sid4; i++) {
                        admin.removeCourse(name4, i);
                    }
                    break;
                case "updateCourseStartTime":
                    /*updateCourseStartTime*/
                    System.out.println("Please enter the Course you want to update");
                    String name = input.nextLine();
                    System.out.println("Please enter the new start time");
                    String newStartTime = input.nextLine();
                    admin.updateCourseStartTime(name, Date.valueOf(newStartTime));
                    break;
                case "updateCourseLocation":
                    /*updateCourseLocation*/
                    System.out.println("Please enter the Course you want to update");
                    String name1 = input.nextLine();
                    System.out.println("Please enter the new location");
                    String newLocation = input.nextLine();
                    admin.updateCourseLocation(name1, newLocation);
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
                        if (events.size() != 0) {
                            PrintCourseEvents(events);
                        } else {
                            System.out.println("No such course");

                        }

                    } else if (args.length == 2) {
                        ArrayList<Event> events = timeTable.searchCourseName(args[1], Date.valueOf("1970-01-01"), Date.valueOf("2100-01-01"));
                        if (events.size() != 0) {
                            PrintCourseEvents(events);
                        } else {
                            System.out.println("No such course");
                        }
                    }
                    break;
                case "SearchExtraByName":
                    /*查询课外活动*/
                    /*SearchExtraByName [name] [start_time](format:yyyy-mm-ll) [end_time]*/
                    if (args.length == 4) {
                        ArrayList<Event> events = timeTable.searchExtraName(args[1], Date.valueOf(args[2]), Date.valueOf(args[3]));
                        if (events.size() != 0) {
                            PrintExtraEvents(events);
                        } else {
                            System.out.println("No such ExtraEvent");
                        }
                    } else if (args.length == 2) {
                        ArrayList<Event> events = timeTable.searchExtraName(args[1], Date.valueOf("1970-01-01"), Date.valueOf("2100-01-01"));
                        if (events.size() != 0) {
                            PrintExtraEvents(events);
                        } else {
                            System.out.println("No such ExtraEvent");
                        }
                    }
                    break;
                case "SearchExtraByTime":
                    /*SearchExtraByTime  [begin_time] [end_time]*/
                    ArrayList<Event> events = timeTable.displayExtra(Date.valueOf(args[1]), Date.valueOf(args[2]));
                    if (events.size() != 0) {
                        PrintExtraEvents(events);
                    } else {
                        System.out.println("No such event");
                    }
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
                        if (events1.size() != 0) {
                            PrintExtraEvents(events1);
                        } else {
                            System.out.println("No such event");

                        }
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
                        if (events1.size() != 0) {
                            PrintExtraEvents(events1);
                        } else {
                            System.out.println("No such event");
                        }
                    }
                    break;
                case "AddClock":
                    System.out.println("Please input the type of activity:(课外活动/临时事务)");
                    String Type = input.nextLine();
                    System.out.println("Please input the name of activity:");
                    String Name = input.nextLine();
                    System.out.println("Please input the start time of activity:(format:yyyy-mm-ll)");
                    String StartTime = input.nextLine();
                    System.out.println("Please input the end time of activity:(format:yyyy-mm-ll))");
                    String EndTime = input.nextLine();
                    System.out.println("Please input the type of Clock:(单次/每天/每周)");
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
                                System.out.println(c.getTime() + ":");
                                if (args[7].equals("个人活动")) {
                                    int ret = Student.addExtra(args[1], null, c.getTime(), 1, 2, 1, 1, Student.getSid(), args[3]);
                                    if (ret != -1) {
                                        if (ret == 1) {
                                            System.out.println("添加成功");
                                        } else if (ret == 2) {
                                            System.out.println(c.getTime() + "添加成功，但是临时事务被覆盖");
                                        }
                                    } else {
                                        System.out.println("添加失败");
                                        ArrayList<java.util.Date> availableTime = timeTable.searchAvailable(c.getTime());
                                        if (timeTable.searchAvailable(c.getTime()).size() != 0) {
                                            System.out.println("可用时间段：");
                                            for (java.util.Date date : availableTime) {
                                                System.out.println(date);
                                            }
                                        } else {
                                            System.out.println("无可用时间段");
                                        }
                                    }
                                } else if (args[7].equals("集体活动")) {
                                    int ret = Student.addExtra(args[1], null, c.getTime(), 2, 2, 1, 1, Student.getSid(), args[3]);
                                    if (ret != -1) {
                                        if (ret == 1) {
                                            System.out.println("添加成功");
                                        } else if (ret == 2) {
                                            System.out.println(c.getTime() + "添加成功，但是临时事务被覆盖");
                                        }
                                    } else {
                                        System.out.println("添加失败");
                                        ArrayList<java.util.Date> availableTime = timeTable.searchAvailable(c.getTime());
                                        if (timeTable.searchAvailable(c.getTime()).size() != 0) {
                                            System.out.println("可用时间段：");
                                            for (java.util.Date date : availableTime) {
                                                System.out.println(date);
                                            }
                                        } else {
                                            ArrayList<java.util.Date> availableTime2 = timeTable.searchLeastUsedTime(c.getTime());
                                            System.out.println("无可用时间段，打印三个使用最少的时间段：");
                                            for (java.util.Date date : availableTime2) {
                                                System.out.println(date);
                                            }
                                        }
                                    }
                                }
                                c.add(Calendar.DATE, 1);
                            }
                        } else if (args[5].equals("once")) {
                            if (args[6].equals("个人活动")) {
                                int ret = Student.addExtra(args[1], null, Date.valueOf(args[4]), 1, 2, 1, 1, Student.getSid(), args[3]);
                                if (ret != -1) {
                                    if (ret == 1) {
                                        System.out.println("添加成功");
                                    } else if (ret == 2) {
                                        System.out.println("添加成功，但是临时事务被覆盖");
                                    }
                                } else {
                                    System.out.println("添加失败");
                                    ArrayList<java.util.Date> availableTime = timeTable.searchAvailable(Date.valueOf(args[4]));
                                    if (timeTable.searchAvailable(Date.valueOf(args[4])).size() != 0) {
                                        System.out.println("可用时间段：");
                                        for (java.util.Date date : availableTime) {
                                            System.out.println(date);
                                        }
                                    } else {
                                        System.out.println("无可用时间段");
                                    }
                                }
                            } else if (args[6].equals("集体活动")) {
                                int ret = Student.addExtra(args[1], null, Date.valueOf(args[4]), 2, 2, 1, 1, Student.getSid(), args[3]);
                                if (ret != -1) {
                                    if (ret == 1) {
                                        System.out.println("添加成功");
                                    } else if (ret == 2) {
                                        System.out.println("添加成功，但是临时事务被覆盖");
                                    }
                                } else {
                                    System.out.println("添加失败");
                                    ArrayList<java.util.Date> availableTime = timeTable.searchAvailable(Date.valueOf(args[4]));
                                    if (timeTable.searchAvailable(Date.valueOf(args[4])).size() != 0) {
                                        System.out.println("可用时间段：");
                                        for (java.util.Date date : availableTime) {
                                            System.out.println(date);
                                        }
                                    } else {
                                        ArrayList<java.util.Date> availableTime2 = timeTable.searchLeastUsedTime(Date.valueOf(args[4]));
                                        System.out.println("无可用时间段，打印三个使用最少的时间段：");
                                        for (java.util.Date date : availableTime2) {
                                            System.out.println(date);
                                        }
                                    }
                                }
                            }
                        } else if (args[5].equals("weekly")) {
                            int weeklast = Integer.parseInt(args[6]);
                            if (args[7].equals("个人活动")) {
                                for (int i = 0; i < weeklast; i++) {
                                    int ret = Student.addExtra(args[1], null, Date.valueOf(args[4]), 1, 2, 1, 1, Student.getSid(), args[3]);
                                    if (ret != -1) {
                                        if (ret == 1) {
                                            System.out.println("添加成功");
                                        } else if (ret == 2) {
                                            System.out.println("添加成功，但是临时事务被覆盖");
                                        }
                                    } else {
                                        System.out.println("添加失败");
                                        ArrayList<java.util.Date> availableTime = timeTable.searchAvailable(Date.valueOf(args[4]));
                                        if (timeTable.searchAvailable(Date.valueOf(args[4])).size() != 0) {
                                            System.out.println("可用时间段：");
                                            for (java.util.Date date : availableTime) {
                                                System.out.println(date);
                                            }
                                        } else {
                                            System.out.println("无可用时间段");
                                        }
                                    }

                                }
                            } else if (args[7].equals("集体活动")) {
                                for (int i = 0; i < weeklast; i++) {
                                    int ret = Student.addExtra(args[1], null, Date.valueOf(args[4]), 2, 2, 1, 1, Student.getSid(), args[3]);
                                    if (ret != -1) {
                                        if (ret == 1) {
                                            System.out.println("添加成功");
                                        } else if (ret == 2) {
                                            System.out.println("添加成功，但是临时事务被覆盖");
                                        }
                                    } else {
                                        System.out.println("添加失败");
                                        ArrayList<java.util.Date> availableTime = timeTable.searchAvailable(Date.valueOf(args[4]));
                                        if (timeTable.searchAvailable(Date.valueOf(args[4])).size() != 0) {
                                            System.out.println("可用时间段：");
                                            for (java.util.Date date : availableTime) {
                                                System.out.println(date);
                                            }
                                        } else {
                                            ArrayList<java.util.Date> availableTime2 = timeTable.searchLeastUsedTime(Date.valueOf(args[4]));
                                            System.out.println("无可用时间段，打印三个使用最少的时间段：");
                                            for (java.util.Date date : availableTime2) {
                                                System.out.println(date);
                                            }
                                        }
                                    }

                                }
                            }
                        }

                    } else if (args[2].equals("offline")) {
                        if (args[5].equals("daily")) {
                            int cycleLast = Integer.parseInt(args[6]);
                            Calendar c = Calendar.getInstance();
                            c.setTime(Date.valueOf(args[4]));
                            for (int i = 0; i < cycleLast; i++) {
                                System.out.println(c.getTime() + ":");
                                if (args[7].equals("个人活动")) {
                                    int ret = Student.addExtra(args[1], args[3], c.getTime(), 1, 2, 1, 1, Student.getSid(), null);
                                    if (ret != -1) {
                                        if (ret == 1) {
                                            System.out.println("添加成功");
                                        } else if (ret == 2) {
                                            System.out.println(c.getTime() + "添加成功，但是临时事务被覆盖");
                                        }
                                    } else {
                                        System.out.println("添加失败");
                                        ArrayList<java.util.Date> availableTime = timeTable.searchAvailable(c.getTime());
                                        if (timeTable.searchAvailable(c.getTime()).size() != 0) {
                                            System.out.println("可用时间段：");
                                            for (java.util.Date date : availableTime) {
                                                System.out.println(date);
                                            }
                                        } else {
                                            System.out.println("无可用时间段");
                                        }
                                    }
                                } else if (args[7].equals("集体活动")) {
                                    int ret = Student.addExtra(args[1], args[3], c.getTime(), 2, 2, 1, 1, Student.getSid(), null);
                                    if (ret != -1) {
                                        if (ret == 1) {
                                            System.out.println("添加成功");
                                        } else if (ret == 2) {
                                            System.out.println(c.getTime() + "添加成功，但是临时事务被覆盖");
                                        }
                                    } else {
                                        System.out.println("添加失败");
                                        ArrayList<java.util.Date> availableTime = timeTable.searchAvailable(c.getTime());
                                        if (timeTable.searchAvailable(c.getTime()).size() != 0) {
                                            System.out.println("可用时间段：");
                                            for (java.util.Date date : availableTime) {
                                                System.out.println(date);
                                            }
                                        } else {
                                            ArrayList<java.util.Date> availableTime2 = timeTable.searchLeastUsedTime(c.getTime());
                                            System.out.println("无可用时间段，打印三个使用最少的时间段：");
                                            for (java.util.Date date : availableTime2) {
                                                System.out.println(date);
                                            }
                                        }
                                    }
                                }
                                c.add(Calendar.DATE, 1);
                            }
                        } else if (args[5].equals("once")) {
                            if (args[6].equals("个人活动")) {
                                int ret = Student.addExtra(args[1], args[3], Date.valueOf(args[4]), 1, 2, 1, 1, Student.getSid(), null);
                                if (ret != -1) {
                                    if (ret == 1) {
                                        System.out.println("添加成功");
                                    } else if (ret == 2) {
                                        System.out.println("添加成功，但是临时事务被覆盖");
                                    }
                                } else {
                                    System.out.println("添加失败");
                                    ArrayList<java.util.Date> availableTime = timeTable.searchAvailable(Date.valueOf(args[4]));
                                    if (timeTable.searchAvailable(Date.valueOf(args[4])).size() != 0) {
                                        System.out.println("可用时间段：");
                                        for (java.util.Date date : availableTime) {
                                            System.out.println(date);
                                        }
                                    } else {
                                        System.out.println("无可用时间段");
                                    }
                                }
                            } else if (args[6].equals("集体活动")) {
                                int ret = Student.addExtra(args[1], args[3], Date.valueOf(args[4]), 2, 2, 1, 1, Student.getSid(), null);
                                if (ret != -1) {
                                    if (ret == 1) {
                                        System.out.println("添加成功");
                                    } else if (ret == 2) {
                                        System.out.println("添加成功，但是临时事务被覆盖");
                                    }
                                } else {
                                    System.out.println("添加失败");
                                    ArrayList<java.util.Date> availableTime = timeTable.searchAvailable(Date.valueOf(args[4]));
                                    if (timeTable.searchAvailable(Date.valueOf(args[4])).size() != 0) {
                                        System.out.println("可用时间段：");
                                        for (java.util.Date date : availableTime) {
                                            System.out.println(date);
                                        }
                                    } else {
                                        ArrayList<java.util.Date> availableTime2 = timeTable.searchLeastUsedTime(Date.valueOf(args[4]));
                                        System.out.println("无可用时间段，打印三个使用最少的时间段：");
                                        for (java.util.Date date : availableTime2) {
                                            System.out.println(date);
                                        }
                                    }
                                }
                            }
                        } else if (args[5].equals("weekly")) {
                            int weeklast = Integer.parseInt(args[6]);
                            if (args[7].equals("个人活动")) {
                                for (int i = 0; i < weeklast; i++) {
                                    int ret = Student.addExtra(args[1], args[3], Date.valueOf(args[4]), 1, 2, 1, 1, Student.getSid(), null);
                                    if (ret != -1) {
                                        if (ret == 1) {
                                            System.out.println("添加成功");
                                        } else if (ret == 2) {
                                            System.out.println("添加成功，但是临时事务被覆盖");
                                        }
                                    } else {
                                        System.out.println("添加失败");
                                        ArrayList<java.util.Date> availableTime = timeTable.searchAvailable(Date.valueOf(args[4]));
                                        if (timeTable.searchAvailable(Date.valueOf(args[4])).size() != 0) {
                                            System.out.println("可用时间段：");
                                            for (java.util.Date date : availableTime) {
                                                System.out.println(date);
                                            }
                                        } else {
                                            System.out.println("无可用时间段");
                                        }
                                    }

                                }
                            } else if (args[7].equals("集体活动")) {
                                for (int i = 0; i < weeklast; i++) {
                                    int ret = Student.addExtra(args[1], args[3], Date.valueOf(args[4]), 2, 2, 1, 1, Student.getSid(), null);
                                    if (ret != -1) {
                                        if (ret == 1) {
                                            System.out.println("添加成功");
                                        } else if (ret == 2) {
                                            System.out.println("添加成功，但是临时事务被覆盖");
                                        }
                                    } else {
                                        System.out.println("添加失败");
                                        ArrayList<java.util.Date> availableTime = timeTable.searchAvailable(Date.valueOf(args[4]));
                                        if (timeTable.searchAvailable(Date.valueOf(args[4])).size() != 0) {
                                            System.out.println("可用时间段：");
                                            for (java.util.Date date : availableTime) {
                                                System.out.println(date);
                                            }
                                        } else {
                                            ArrayList<java.util.Date> availableTime2 = timeTable.searchLeastUsedTime(Date.valueOf(args[4]));
                                            System.out.println("无可用时间段，打印三个使用最少的时间段：");
                                            for (java.util.Date date : availableTime2) {
                                                System.out.println(date);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "AddTempo":
                    /*AddTempo [name]  [start_time](format:yyyy-mm-ll)  [location]*/
                    if(Student.addTempo(args[1], args[3], Date.valueOf(args[2]), 0, 3, 0, 1, Student.getSid(), null)){
                        System.out.println("添加成功");
                    } else  {
                        System.out.println("有其他的课程或课外活动,添加失败");
                    }
                    break;
                case "SearchTempoByName":
                    /*SearchTempoByTime [name] [start_time](format:yyyy-mm-ll)  [end_time](format:yyyy-mm-ll)*/
                    ArrayList<Event> SearchResult3 = timeTable.searchTempoName(args[1], Date.valueOf(args[2]), Date.valueOf(args[3]));
                if(SearchResult3.size()!=0){
                    for (Event event : SearchResult3) {
                        System.out.println("Tempo:");
                        for (Tempo tempo : event.getTempo()) {
                            if (tempo.name.equals(args[1])) {
                                System.out.println(event.getTime() + tempo.name + " " + tempo.location);
                            }
                        }
                    }
                }else{
                    System.out.println("无结果");
                }

                    break;
                case "SearchTempoByTime":
                    /*SearchTempoByTime [start_time](format:yyyy-mm-ll)  [end_time](format:yyyy-mm-ll)*/
                    ArrayList<Event> SearchResult4 = timeTable.displayTempo(Date.valueOf(args[1]), Date.valueOf(args[2]));
                if(SearchResult4.size()!=0){
                    for (Event event : SearchResult4) {
                        System.out.println("Tempo:");
                        for (Tempo tempo : event.getTempo()) {
                            System.out.println(event.getTime() + tempo.name + " " + tempo.location);
                        }
                    }
                }else{
                    System.out.println("无结果");
                }
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
