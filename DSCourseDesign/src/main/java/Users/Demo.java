package Users;

import Dao.GetSession;
import Users.tool.EventForRec;
import mapper.GraphMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Demo {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Set<String> stringSet = new HashSet<>();
        SqlSession sesssion = GetSession.getSesssion();
        GraphMapper mapper = sesssion.getMapper(GraphMapper.class);
        while (input.hasNext()) {
            String str = input.nextLine();
            String[] strs = str.split("\\s+");
            String[] temp=new String[2];
            for (int i = 0; i < 2; i++) {
                stringSet.add(strs[i]);
                temp[i]=strs[i].split(":")[0];
            }
            mapper.addRoad(temp[0],temp[1],Integer.parseInt(strs[2]));
        }
        for (String s : stringSet) {
            int x,y;
            String[] temp1=s.split(":");
            String[] temp2=temp1[1].split(",");
            x=Integer.parseInt(temp2[0]);
            y=Integer.parseInt(temp2[1]);
            mapper.addNode(temp1[0],x,y);
        }
        sesssion.commit();
        sesssion.close();
    }
}
