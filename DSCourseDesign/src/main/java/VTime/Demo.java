package VTime;

import java.beans.VetoableChangeListener;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Demo {
    public static void main(String[] args) throws ParseException, InterruptedException, IOException {
//        Date date = new Date(0L);
//        long time = date.getTime();
//        time = time+1000L*60*60*24*365;
//        date.setTime(time);
//        System.out.println(date);

//        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
//        Date date = new Date();
//        String str = simpleDateFormat.format(date);
//        System.out.println(str);

//        String str = "2023-11-11 11:11:11";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date d = sdf.parse(str);
//        System.out.println(d);

//        Calendar c = Calendar.getInstance();
//        Date d = new Date(0L);
//        c.setTime(d);
//        int i = c.get(Calendar.YEAR);
//        System.out.println(i);

//
//        ZoneId zoneId = ZoneId.of("Asia/Pontianak");
//        System.out.println(zoneId);

//        Instant now = Instant.now();
//        System.out.println(now);
//
//        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.of("Asia/Shanghai"));
//        System.out.println(zonedDateTime);

//        ZonedDateTime time  =Instant.now().atZone(ZoneId.of("Asia/Shanghai"));
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss EE a");
//        System.out.println(dtf.format(time));

//        LocalDate nowDate = LocalDate.now();
//        LocalDate date = LocalDate.of(2023, 2, 3);
//        Month month = date.getMonth();
//        System.out.println(month);

//        LocalDate now = LocalDate.now();
//        LocalDate of = LocalDate.of(2000, 1, 1);
//
//        Period between = Period.between(now, of);
//        System.out.println(between);
//        Calendar Vcalendar = Calendar.getInstance();

//        DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//        while(true){
//            Thread.sleep(1000);
//            System.out.println(sdf.format(Vcalendar.getTime()));
//        }


//        class TimerTaskTest extends TimerTask{
//            @Override
//            public void run() {
//                Vcalendar.add(Calendar.MINUTE,1);
//                SimpleDateFormat sdf  = new SimpleDateFormat("HH:mm:ss");
//                System.out.println("虚拟时间："+sdf.format(Vcalendar.getTime())+"-------"+"实际时间："+
//                        sdf.format(Calendar.getInstance().getTime()));
//            }
//        }
//
//
//        Timer timer = new Timer();
//        timer.schedule(new TimerTaskTest(),1000L,2000L);

        VirtualTime Vtime = new VirtualTime();
//        System.out.println(VirtualTime.getMonth());
//        System.out.println(VirtualTime.getDay());
//        System.out.println(VirtualTime.getHours());
//        System.out.println(VirtualTime.getDayOfWeek());
        Vtime.TimeStart();
//
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        while(true){
            if(x==1){
//                System.out.println(x);
                System.out.println("时钟暂停");
//                System.out.println(VirtualTime.getTime());
                Vtime.pause();
            }else if(x==2){
//                System.out.println(x);
                System.out.println("时钟恢复");
                Vtime.restart();
            }else if (x==3){
//                System.out.println(x);
                Vtime.FF();
                System.out.println("时钟快进，当前速度为现实"+VirtualTime.getRate()+"秒=系统时钟1小时");
            }
            x=scanner.nextInt();
        }








    }
}
