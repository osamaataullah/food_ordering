package food_ordering;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Restaurant {

    static int user_res;

    public void view_restaurant(String city) {
        Connection myconn = Database.connector();

        String res_query = "SELECT * FROM restaurant WHERE city=\"" + city + "\"";
        // System.out.println(res_query);

        try {
            PreparedStatement res_ps = myconn.prepareStatement(res_query);
            ResultSet rs = res_ps.executeQuery();
            System.out.println("Restaurant no." + "         " + "Restaurant Name" + "         " + "City Area");
            while (rs.next()) {
                System.out.println(rs.getString(1) + "                   " + rs.getString(2) + "                      "
                        + rs.getString(4));
            }
            select_restaurant();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void select_restaurant() {
        System.out.println("Select Resaturant to View Menu");
        user_res = App.sc.nextInt();
        Menu menu = new Menu();
        menu.view_menu(user_res);
    }

}
