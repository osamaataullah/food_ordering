package food_ordering;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int totalCost, totalEstimatedTime;
    Connection myconn = Database.connector();

    private static Cart instance = null;

    public static Cart getInstance(){
        if(instance == null){
            instance= new Cart();
        }
        return instance;
    }
    private Cart() {}

    public void add_to_cart(List<Integer> item_num, List<Integer> item_qty){
        try {
            String query = "insert into orders(user_id,menu_id,quantity,order_status,timestamp) values(?,?,?,'ADDED_TO_CART',datetime('now'))";
            PreparedStatement ps = myconn.prepareStatement(query);

            for( int i = 0 ; i < item_num.size() ; i++){
                ps.setInt(1, Login.user_id);
                ps.setInt(2,item_num.get(i));
                ps.setInt(3, item_qty.get(i));
                ps.execute();
            }
            System.out.println("Items added to cart successfully");
            display_cart();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void display_cart(){
        System.out.println("Loading your cart...");
        String query = "select m.estimated_time, o.menu_id, m.menu_name, o.quantity, o.quantity * m.price  from orders as o inner join menu as m on o.menu_id = m.menu_id  and o.order_status = 'ADDED_TO_CART' and o.user_id =" + Login.user_id;
        try {
            System.out.println("ID\t" + "menu name\t" + "Quantity\t" + "Total price");
            System.out.println("---------------------------------------------------------");
            PreparedStatement ps = myconn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            totalCost = 0;
            totalEstimatedTime = 0;
            if (!rs.isBeforeFirst()) {
                System.out.println("No Items present in cart.");
            } else {
                while (rs.next()) {
                    totalCost += rs.getInt(5);
                    totalEstimatedTime += rs.getInt(1);
                    System.out.println(rs.getInt("menu_id") + "\t" +
                            rs.getString("menu_name") + "\t" +
                            rs.getDouble("quantity") + "\t" +
                            " ₹" + rs.getInt(5));
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("---------------------------------------------------------");
        System.out.println("Total Cost: ₹" + totalCost);
        System.out.println("Estimated Time for delivery: " + totalEstimatedTime + "mins");
        System.out.println("---------------------------------------------------------");
        System.out.println("Select an option");
        System.out.println("1. Back to menu\n2. Remove Items from cart\n3. Proceed to checkout\n4. Discard all items\n5. Exit.");
        int inp = App.sc.nextInt();
        if(inp == 1){
            Menu m = Menu.getInstance();
            m.view_menu(Login.user_city);
        }
        else if (inp == 2)
            remove_items();
        else if(inp == 3){
            payment payu = new payment();
            int bill = payu.Calculate_Bill();
            // System.out.println("Pay Money");

            //DoPayment class object
            DoPayment dopayment = new DoPayment();
            dopayment.doPayment(bill);
            

            }
        else if(inp == 4){
            discard_all();
        }
        else if(inp == 5) {
            System.out.println("Thank you for visiting us! Have a nice day :)");
            System.exit(0);
        }



    }

    public void discard_all(){
        String query = "delete from orders where user_id = ? and order_status = 'ADDED_TO_CART'";
        try{
            PreparedStatement ps = myconn.prepareStatement(query);
            ps.setInt(1, Login.user_id);
            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        display_cart();
    }

    public void remove_items(){
        List<Integer> ids = new ArrayList<>();
        while(true) {
            System.out.println("Enter item id (menu_id) to remove.");
            ids.add(App.sc.nextInt()); App.sc.nextLine();
            System.out.println("Wanna remove more items? (y/n):");
            String y_n = App.sc.nextLine();
            if (y_n.equalsIgnoreCase("n"))
                break;
        }
        String query = "delete from orders where user_id = ? and order_status = 'ADDED_TO_CART' and menu_id = ?";
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
        display_cart();

    }


}
