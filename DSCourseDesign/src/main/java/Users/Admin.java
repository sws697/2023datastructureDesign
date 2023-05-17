package Users;

/**
 * 管理员类，目前未具体实现
 * @author maxiaotiao
 */
public class Admin {
    private String username="admin";
    private String password="admin";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public boolean login(String username,String password){
        if(this.username.equals(username)&this.password.equals(password)){
            return true;
        }
        else return false;
    }

    public void addStu(int sid,String password,String location){

    }

}
