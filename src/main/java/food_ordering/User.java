package food_ordering;

public class User {
    protected static int user_id;
    protected static String city;
    protected static String userName;
    protected static String userEmail;
   

    public String getUserCity() {
        return this.city;
    }

    

    public int getUser_id() {
        return this.user_id;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public String getUserName() {
        return this.userName;
    }
    


}
