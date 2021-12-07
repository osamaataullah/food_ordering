package food_ordering;

import java.util.Scanner;

public class Login{
    static int user_id;
    static String user_city;
    public void user(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter Your Email");
        String email = sc.nextLine();
        System.out.println("Enter Your Password");
        String password = sc.nextLine();

        Database mydb = new Database();

        String[] datakey = {"email", "password"};
        String[] datavalue = {email, password};

        boolean res = mydb.verifyData("user", datakey, datavalue);
        if(res){
            System.out.println("Login Successful");
            user_city = mydb.getUserCity(user_id);
            //User usr = new User(user_id, city);
            Menu m = Menu.getInstance();
            m.view_menu(user_city);
        }else{
            System.out.println("User Not Found, Register first");
        }

        sc.close();
    }


}