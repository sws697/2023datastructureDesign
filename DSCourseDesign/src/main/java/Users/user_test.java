package Users;

public class user_test {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "user_test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
