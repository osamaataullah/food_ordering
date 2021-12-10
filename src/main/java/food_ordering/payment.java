package food_ordering;

import java.sql.*;
public class payment {
    
    Connection myconn = Database.connector();
    public int Calculate_Bill()
    {
        int tcost =0; 
        try{
            
            String query = "Select m1.price, o1.quantity from menu m1 join orders o1 on m1.menu_id=o1.menu_id where o1.order_status = 'ADDED_TO_CART' AND o1.user_id ="+Login.user_id;
            PreparedStatement ps = myconn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
             tcost = tcost + rs.getInt(1)*rs.getInt(2);   
            }
            
            if(tcost>=100){
            System.out.println("BASE AMOUNT = "+tcost);
            String q2 = "Select promos from user where id ="+Login.user_id;
            PreparedStatement ps2 = myconn.prepareStatement(q2);
            ResultSet rs2 = ps2.executeQuery();
            int c = rs2.getInt(1);
            if(c == 3)
            {
                System.out.println("Enter 1 to use SAVE50 or 2 for SAVE20");
                int pro = App.sc.nextInt();
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
            String query1 = "UPDATE orders set order_status = 'payamount' where order_status = 'ADDED_TO_CART' AND user_id="+Login.user_id;
            PreparedStatement ps1 = myconn.prepareStatement(query1);
            ps1.execute();
            int user_delivery_charges = calculate_delivery_charges();
            System.out.println("Applying delivery fee of Rs. "+user_delivery_charges);
            tcost = tcost+user_delivery_charges;
            System.out.println("Total Cost to pay "+tcost);
            
            }
            else
            System.out.println("Insufficient Items please order more food for delivery min "+ (100-tcost) + " rupees more items");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tcost;
    }

    private int calculate_delivery_charges(){
        Connection myconn1 = Database.connector();
        String restaurant_area ="" ;
        String user_area="" ;
        int distance=0;
        int delivery_Charges=0;
        String restaurant_area_query = "SELECT city_area FROM restaurant where restaurant_id="+Restaurant.user_res;
        String user_area_query = "SELECT city_area FROM user where id="+Login.user_id;

        PreparedStatement ps_1,ps_2,ps_distance,ps_4;
        try {
            ps_1 = myconn1.prepareStatement(restaurant_area_query);
            ps_2 = myconn1.prepareStatement(user_area_query);

            ResultSet rs_1 = ps_1.executeQuery();
            ResultSet rs_2 = ps_2.executeQuery();

             restaurant_area = rs_1.getString(1);
             user_area = rs_2.getString(1);
             String distance_query = "SELECT distance FROM city_area_distance where area1=\"" + restaurant_area +"\" and area2=\"" + user_area +"\"";


             ps_distance = myconn1.prepareStatement(distance_query);
             ResultSet rs_distance = ps_distance.executeQuery();
            distance = rs_distance.getInt(1);

            String delivery_Charge_query = "SELECT delivery_charges from delivery_charges WHERE distance ="+distance;

             ps_4 = myconn1.prepareStatement(delivery_Charge_query);
             ResultSet rs_4 = ps_4.executeQuery();
             delivery_Charges = rs_4.getInt(1);





        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //  distance   (in km)     delivery charges(in Rs.)
        //     0                   0
        //     5                    20
        //     10                   40
        //      20                  60

        
        return delivery_Charges;
       

    }
    
}