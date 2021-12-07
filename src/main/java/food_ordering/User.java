package food_ordering;

public class User {
    private int user_id;
    private String city;

    public User(int user_id, String city) {
        this.user_id = user_id;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


}
