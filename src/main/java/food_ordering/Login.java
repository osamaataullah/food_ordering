package food_ordering;

import java.util.Scanner;

public class Login{


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
            System.out.println("Login Succesfull");
        }else{
            System.out.println("User Not Found, Register first");
        }

        sc.close();
    }


}