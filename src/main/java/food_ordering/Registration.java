package food_ordering;

import java.util.Scanner;

public class Registration{

    public void createNewUser(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Your Name");
        String name = sc.nextLine();
        System.out.println("Enter Your Email");
        String email = sc.nextLine();
        System.out.println("Enter Your  Password");
        String password = sc.nextLine();
        System.out.println("Enter you city");
        String city = sc.nextLine();
        Database mydb = new Database();

        String[] datakey = {"name", "email", "password","city"};
        String[] datavalue = {name, email, password, city};
        mydb.insert("user", datakey, datavalue);

        System.out.println("Welcome to Food Ordering (:");
        sc.close();
    }
    

}