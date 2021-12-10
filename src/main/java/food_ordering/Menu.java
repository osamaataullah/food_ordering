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

    public static Menu getInstance() {
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    Menu() {
        this.myconn = Database.connector();
    }

    // displays menu from restaurants in the same city in which user is.
    public void view_menu(int res_id) {
        /*function to view the menu of a particular resturant*/
        PreparedStatement mystmt = null;
        ResultSet rs = null;
        try {
            String query = "select r.restaurant_name, m.menu_id, m.menu_name, m.price, m.estimated_time from menu as m inner join restaurant as r on m.restaurant_id = r.restaurant_id where r.restaurant_id in "
                    +
                    "(select r.restaurant_id from restaurant r where r.restaurant_id =" + res_id + ");";
            // System.out.println(query);
            mystmt = myconn.prepareStatement(query);
            rs = mystmt.executeQuery();

            System.out.println("*****  From restaurants in your area  *****");
            System.out.println("Restaurant\t" + "MenuId\t" + "menu name\t" + "Price (₹)\t" + "Estimated delivery time");
            System.out.println("---------------------------------------------------------");

            while (rs.next()) {
                System.out.println(rs.getString("restaurant_name") + "\t" +
                        rs.getInt("menu_id") + "\t" +
                        rs.getString("menu_name") + "\t" +
                        "₹" + rs.getDouble("price") + "\t" +
                        rs.getInt("estimated_time") + " mins");
            }

            mystmt.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        order();
    }

    public void itemSelection() {
        /*function to select a item*/
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Select the item number:");
            int item = sc.nextInt();
            item_num.add(item);

            System.out.println("Select quantity:");
            int qty = sc.nextInt();
            sc.nextLine();
            item_qty.add(qty);

            System.out.println("Wanna add more? (y/n):");
            String y_n = sc.nextLine();
            if (y_n.equalsIgnoreCase("n"))
                break;
        }
    }

    public void order() {
        Scanner sc = new Scanner(System.in);
        System.out.println("---------------------------------------------------------");
        System.out.println("Please select an option.");
        System.out.println(
                "1. Add to cart\n2. Add to Wishlist.\n3. Go To Cart.\n4. Go to Wishlist.\n5. Back to Main Menu.\n6. Exit");
        int inp = sc.nextInt();
        if (inp == 1) {
            itemSelection();
            System.out.println("Adding to cart...");
            Cart crt = Cart.getInstance();
            crt.add_to_cart(item_num, item_qty);
            item_num.clear();
            item_qty.clear();
        } else if (inp == 2) {
            itemSelection();
            System.out.println("Adding to Wishlist...");
            Wishlist wl = Wishlist.getInstance();
            wl.add_to_wishlist(item_num, item_qty);
            item_num.clear();
            item_qty.clear();
        } else if (inp == 3) {
            Cart c = Cart.getInstance();
            c.display_cart();
        } else if (inp == 4) {
            Wishlist w = Wishlist.getInstance();
            w.display_wishlist();
        } else if (inp == 5) {
            Login login = new Login();
            login.mainMenuPanel();
        } else {
            System.out.println("Thank you for visiting us! Have a nice day :)");
            System.exit(0);
        }
    }

}
