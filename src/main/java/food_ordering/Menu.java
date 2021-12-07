package food_ordering;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    Connection myconn;
    private List<Integer> item_num = new ArrayList<>();
    private List<Integer> item_qty = new ArrayList<>();
    private static Menu instance = null;

    public static Menu getInstance(){
        if(instance == null){
            instance= new Menu();
        }
        return instance;
    }


    private Menu() {
        this.myconn = Database.connector();
    }


    //displays menu from restaurants in the same city in which user is.
    public void view_menu(String city) {
        PreparedStatement mystmt = null;
        ResultSet rs = null;
        try {
            String query = "select menu_id, menu_name, price, estimated_time from menu where restaurant_id in " +
                    "(select r.restaurant_id from restaurant r where r.city = \"" + city +"\" )";
            //System.out.println(query);
            mystmt = myconn.prepareStatement(query);
            rs = mystmt.executeQuery();

            System.out.println("*****  From restaurants in your area  *****");
            System.out.println("ID\t" + "menu name\t" + "Price (₹)\t" + "Estimated delivery time");
            System.out.println("---------------------------------------------------------");

            while (rs.next()) {
                    System.out.println(rs.getInt("menu_id") + "\t" +
                            rs.getString("menu_name") + "\t" +
                            "₹"+ rs.getDouble("price") + "\t" +
                            rs.getInt("estimated_time") + " mins");
                }

            mystmt.close();
            rs.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        order();
    }

    public void itemSelection(){
        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("Select the item number:");
            int item = sc.nextInt();
            item_num.add(item);

            System.out.println("Select quantity:");
            int qty = sc.nextInt();sc.nextLine();
            item_qty.add(qty);

            System.out.println("Wanna add more? (y/n):");
            String y_n = sc.nextLine();
            if(y_n.equalsIgnoreCase("n"))
                break;
        }
    }


    public void order(){
        Scanner sc = new Scanner(System.in);
        System.out.println("---------------------------------------------------------");
        System.out.println("Please select an option.");
        System.out.println("1. Add to cart\n2. Add to Wishlist.\n3. Go To Cart.\n4. Go to Wishlist.");
        int inp = sc.nextInt();
        if(inp == 1){
            itemSelection();
            System.out.println("Adding to cart...");
            Cart crt = Cart.getInstance();
            crt.add_to_cart(item_num, item_qty);
        }
        else if(inp == 2){
            itemSelection();
            System.out.println("Adding to Wishlist...");
            Wishlist wl = Wishlist.getInstance();
            wl.add_to_wishlist(item_num, item_qty);
        }
        else if(inp == 3){
            Cart c = Cart.getInstance();
            c.display_cart();
        }
        else if(inp == 4){
            Wishlist w = Wishlist.getInstance();
            w.display_wishlist();
        }
    }

}
