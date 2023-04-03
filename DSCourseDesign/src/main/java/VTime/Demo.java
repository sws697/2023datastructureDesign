package VTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class Demo {
    public static void main(String[] args) throws ParseException {
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

        ZonedDateTime time  =Instant.now().atZone(ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss EE a");
        System.out.println(dtf.format(time));


    }
}
