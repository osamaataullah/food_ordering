package food_ordering;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ratings {
    Connection myconn = Database.connector();
    List<Integer> items_delivered_ids = new ArrayList<>();
    List<String> items_delivered_name = new ArrayList<>();
    List<Integer> items_ratings = new ArrayList<>();

    public boolean foodDelivered() {
        boolean ans = false;
        String query = "select * from orders as o inner join menu as m on o.menu_id = m.menu_id where order_status = 'DELIVERED' and user_id = "
                + Login.user_id;
        try {
            PreparedStatement ps = myconn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("No Items.");
                return ans;
            } else {
                while (rs.next()) {
                    int menu_id = rs.getInt("menu_id");
                    String name = rs.getString("menu_name");
                    items_delivered_ids.add(menu_id);
                    items_delivered_name.add(name);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ans = true;
        return ans;
    }

    public void rate_items() {
        /*function to rate items*/
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < items_delivered_name.size(); i++) {
            System.out.println("Rate this product on a scale of 1-5 --> " + items_delivered_name.get(i));
            int rate = sc.nextInt();
            items_ratings.add(rate);
        }

        try {
            String query = "insert into rating(user_id, menu_id, rating) values(?,?,?)";
            PreparedStatement ps = myconn.prepareStatement(query);

            for (int i = 0; i < items_ratings.size(); i++) {
                ps.setInt(1, Login.user_id);
                ps.setInt(2, items_delivered_ids.get(i));
                ps.setInt(3, items_ratings.get(i));
                ps.execute();
            }
            System.out.println("Thanks for your feedback.");
            System.exit(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
