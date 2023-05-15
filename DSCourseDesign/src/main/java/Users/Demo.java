package Users;

import Users.tool.EventForRec;

import java.util.ArrayList;

public class Demo {
    public static void main(String[] args) {
        ArrayList<EventForRec> events = Student.getEvents(2021211115);
        System.out.println(events);
    }
}
