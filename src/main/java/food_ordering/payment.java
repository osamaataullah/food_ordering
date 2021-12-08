package food_ordering;

import java.sql.*;
import java.util.Scanner;
public class payment {
    
    Scanner sc = new Scanner(System.in);
    public void Calculate_Bill()
    {
        try{
            Connection myconn = Database.connector();
            String query = "Select m1.price, o1.quantity from menu m1 join orders o1 on m1.menu_id=o1.menu_id where o1.order_status = 'ADDED_TO_CART' AND o1.user_id ="+Login.user_id;
            PreparedStatement ps = myconn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int tcost =0; 
            while(rs.next())
            {
             tcost = tcost + rs.getInt(1)*rs.getInt(2);   
            }
            
            if(tcost>100){
            System.out.println("BASE AMOUNT = "+tcost);
            String q2 = "Select promos from user where id ="+Login.user_id;
            PreparedStatement ps2 = myconn.prepareStatement(q2);
            ResultSet rs2 = ps2.executeQuery();
            int c = rs2.getInt(1);
            if(c == 3)
            {
                System.out.println("Enter 1 to use SAVE50 or 2 for SAVE20");
                int pro = sc.nextInt();
                if(pro ==1)
                tcost = tcost - 50;
                else if(pro ==2)
                tcost = tcost - 20;
                String q3 = "UPDATE user set promos = "+pro+" where id ="+Login.user_id;
                PreparedStatement ps3 = myconn.prepareStatement(q3);
                ps3.execute();
            }
            else
            {
                if(c == 2){
                    System.out.println("You are left with only Save50, applying it");
                    tcost = tcost - 50;
                }
                else if(c==1){
                    System.out.println("You are left with only Save20, applying it");
                    tcost = tcost - 20;
                }
                String q4 = "UPDATE user set promos = "+0+" where id ="+Login.user_id;
                PreparedStatement ps4 = myconn.prepareStatement(q4);
                ps4.execute();
            }
            System.out.println("Amount after applying promo code = "+tcost);
            String query1 = "UPDATE orders set order_status = 'payamount' where order_status = 'ADDED_TO_CART'";
            PreparedStatement ps1 = myconn.prepareStatement(query1);
            ps1.execute();
            System.out.println("Applying delivery fee of Rs. "+10);
            tcost = tcost+10;
            System.out.println("Total Cost to pay "+tcost);
            }
            else
            System.out.println("Insufficient Items please order more food for delivery min "+ (100-tcost) + " rupees more items");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
