package food_ordering;

import java.util.Scanner;

public class App{
    public static void main(String[] args){


        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Food Ordering System");
        System.out.println("1. Login \n2. Register");

        int userinput = sc.nextInt();
        if(userinput==1){
            Login currentuser = new Login();
            currentuser.user();
        }else if (userinput==2){
            Registration newuser = new Registration();
            newuser.createNewUser();
        }else{
            System.out.println("Enter Valid Inputs");
        }
        sc.close();

    }
}