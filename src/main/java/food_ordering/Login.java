package food_ordering;

import java.util.Scanner;

public class Login extends User {

    Database mydb = new Database();

    public void login() {
        /*Function for login of the user */
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter Your Email");
        String email = sc.nextLine();
        System.out.println("Enter Your Password");
        String password = sc.nextLine();

        String[] datakey = { "email", "password" };
        String[] datavalue = { email, password };

        boolean res = mydb.verifyData("user", datakey, datavalue);
        if (res) {
            System.out.println("Login Successful");
            setUser_id(user_id);
            setUser_city();
            setUserEmail();
            setUserName();
            System.out.println("Welcome " + super.getUserName());

            mainMenuPanel();

        } else {
            System.out.println("User Not Found, Register first");
        }

        sc.close();
    }

    private void setUser_id(int user_id) {
        super.user_id = user_id;
    }

    public void setUser_city() {
        super.city = mydb.getCity(user_id);
    }

    public void setUserName() {
        super.userName = mydb.getName(user_id);
    }

    public void setUserEmail() {
        super.userEmail = mydb.getEmail(user_id);
    }

    public void mainMenuPanel() {
        /*Function to display the menu panel*/
        System.out.println("Select One of the option below");
        System.out.println("1. See Restaurants in your city\n2. Go To Your Cart\n3. Go To Your wishlist");
        int input = App.sc.nextInt();
        if (input == 1) {
            Restaurant restaurant = new Restaurant();
            restaurant.view_restaurant(super.getUserCity());
        } else if (input == 2) {
            Cart cart = new Cart();
            cart.display_cart();
        } else {
            Wishlist wishlist = new Wishlist();
            wishlist.display_wishlist();
        }
    }

}