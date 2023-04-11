package Users;

public class Demo {
    public static void main(String[] args) {
        boolean login = Student.login(2021211115, "123456");
        if(login){
            System.out.println("登录成功");
            System.out.println(Student.getId());
            System.out.println(Student.getPassword());
        }
    }
}
