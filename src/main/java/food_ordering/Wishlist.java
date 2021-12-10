package food_ordering;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Wishlist {
    Connection myconn = Database.connector();

    private static Wishlist instance = null;

    public static Wishlist getInstance(){
        if(instance == null){
            instance= new Wishlist();
        }
        return instance;
    }
    Wishlist() {}

    public void add_to_wishlist(List<Integer> item_num, List<Integer> item_qty){
        try {
            String query = "insert into orders(user_id,menu_id,quantity,order_status,timestamp) values(?,?,?,'ADDED_TO_WISHLIST',datetime('now'))";
            PreparedStatement ps = myconn.prepareStatement(query);

            for( int i = 0 ; i < item_num.size() ; i++){
                ps.setInt(1, Login.user_id);
                ps.setInt(2,item_num.get(i));
                ps.setInt(3, item_qty.get(i));
                ps.execute();
            }
            System.out.println("Items added to Wishlist successfully");
            item_num.clear();
            item_qty.clear();
            display_wishlist();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

}

    public void display_wishlist(){
        boolean empty=false;
        System.out.println("Loading your Wishlist...");
        String query = "select m.estimated_time, o.menu_id, m.menu_name, o.quantity, o.quantity * m.price  from orders as o inner join menu as m on o.menu_id = m.menu_id  and o.order_status = 'ADDED_TO_WISHLIST' and o.user_id =" + Login.user_id;
        try {
            System.out.println("ID\t" + "menu name\t" + "Quantity\t" + "Total price");
            System.out.println("---------------------------------------------------------");
            PreparedStatement ps = myconn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("WishList empty !!");
                empty = true;
            }else {
                while (rs.next()) {
                    System.out.println(rs.getInt("menu_id") + "\t" +
                            rs.getString("menu_name") + "\t" +
                            rs.getDouble("quantity") + "\t" +
                            " ₹" + rs.getInt(5));
                }
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("---------------------------------------------------------");
        System.out.println("Select an option");
        if(empty)
            System.out.println("1. Back to menu");
        else
            System.out.println("1. Back to menu\n2. Move all to Cart\n3. Remove items\n4. Exit");
        //System.out.println("1. Move all to Cart\n2. Remove items\n3. Back to menu");
        int inp = new Scanner(System.in).nextInt();
        if(inp == 1){
            Login login = new Login();
            login.mainMenuPanel();
        }
        else if (inp == 2)
            move_to_cart();
        else if(inp == 3){
            remove_items();
        }
        else if (inp == 4){
            System.out.println("Thank you for visiting us! Have a nice day :)");
            System.exit(0);
        }

    }

    public void remove_items(){
        List<Integer> ids = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("Enter item id (menu_id) to remove.");
            ids.add(sc.nextInt()); sc.nextLine();
            System.out.println("Wanna remove more items? (y/n):");
            String y_n = sc.nextLine();
            if (y_n.equalsIgnoreCase("n"))
                break;
        }
        String query = "delete from orders where user_id = ? and order_status = 'ADDED_TO_WISHLIST' and menu_id = ?";
        try{
            PreparedStatement ps = myconn.prepareStatement(query);
            for(int i = 0; i < ids.size() ; i++) {
                ps.setInt(1, Login.user_id);
                ps.setInt(2,ids.get(i));
                ps.execute();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        display_wishlist();

    }

    public void move_to_cart(){
        String query = "update orders set order_status = 'ADDED_TO_CART' where user_id = ? and order_status = 'ADDED_TO_WISHLIST'";
        try{
            PreparedStatement ps = myconn.prepareStatement(query);
            ps.setInt(1, Login.user_id);
            int rows = ps.executeUpdate();
            System.out.println(rows + " item(s) added to cart.");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Cart c =Cart.getInstance();
        c.display_cart();
    }

}
