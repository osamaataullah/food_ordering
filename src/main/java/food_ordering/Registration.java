package food_ordering;

public class Registration {

    public void createNewUser() {
        System.out.println("Enter Your Name");
        String name = App.sc.next();
        System.out.println("Enter Your Email");
        String email = App.sc.next();
        System.out.println("Enter Your  Password");
        String password = App.sc.next();
        System.out.println("Enter you city");
        String city = App.sc.next();
        System.out.println("Enter you Area in city for Delivery");
        App.sc.nextLine();
        String city_area = App.sc.nextLine();
        Database mydb = new Database();

        String[] datakey = { "name", "email", "password", "city", "city_area" };
        String[] datavalue = { name, email, password, city, city_area };
        boolean isRegister = mydb.insert("user", datakey, datavalue);
        if (isRegister) {
            System.out.println("Registered Succesfully");
            App.viewMainPanel();
        } else {
            System.out.println("Server Down");
        }

    }

}