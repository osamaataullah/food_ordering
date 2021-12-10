package food_ordering;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class Track {

    Connection myconn;
    private String expected_time = "";
    String timestamp;
    int limit;
    boolean late;

    public Track() {
        Cart c = Cart.getInstance();
        this.myconn = Database.connector();
        this.expected_time = "";
        this.timestamp = "";
        this.limit = (int) (0.1 * c.getTotalEstimatedTime()); // calculate 10% of estimated time
        this.late = false;
    }

    public void display_track_menu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Select an option.");
            if (late == false)
                System.out.println("1. Track Order\n2. Exit.\n5. Back To Main Menu");
            else
                System.out.println(
                        "1. Track Order\n2. Exit.\n3. Cancel Orders\n4. Simulate Delivered\n5. Back To Main Menu");

            int inp = sc.nextInt();
            if (inp == 1 && late == false) {
                if (expected_time.equals(""))
                    computeTime();

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
                String cur_time = LocalTime.now().format(dtf);

                computeRemainingTime(cur_time);

                System.out.println("Current Time " + cur_time);
                System.out.println("Your order will be delivered by " + expected_time);
                // System.out.println(remaining_time + " hours remaining !");

            } else if (inp == 1 && late == true) {
                Ratings r = new Ratings();
                boolean delivery_status = r.foodDelivered();
                if (delivery_status)
                    r.rate_items();
                else
                    System.out.println(
                            "Sorry! we're running late unexpectedly.\n Please wait or you may cancel your order.");
                display_track_menu();
            } else if (inp == 2) {
                System.out.println("Thank you for visiting us! Have a nice day :)");
                System.exit(0);
            } else if (inp == 3) {
                cancel_order(); // delete records
                System.out
                        .println("Orders cancelled :(\n Any amount deducted will be refunded in next 5 working days.");
            } else if (inp == 4) {
                simulate_delivered();
            } else {
                Login login = new Login();
                login.mainMenuPanel();
            }

        }
    }

    public void simulate_delivered() {
        String query = "update orders set order_status=\"DELIVERED\" where order_status = 'payment_done' and user_id = "
                + Login.user_id;

        PreparedStatement ps = null;
        try {
            ps = myconn.prepareStatement(query);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        display_track_menu();
    }

    public void computeRemainingTime(String cur_time) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String remaining_time = "";

        try {
            Date d1 = df.parse(expected_time);
            Date d2 = df.parse(cur_time);
            // System.out.println(d1.getTime()+"\n" +d2.getTime());
            long diff = d1.getTime() - d2.getTime();
            // System.out.println(diff);
            if (diff <= 0) {
                late = true;
                display_track_menu();
            }
            remaining_time = df.format(new Date(diff));
            // System.out.println("The diff is " + date3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(remaining_time + " hours remaining !");
    }

    // computes the expected time of delivery
    public void computeTime() {
        Cart c = Cart.getInstance();
        int estimated_time = c.getTotalEstimatedTime();
        // change added_to_cart status to payment_done
        String query = "select * from orders where order_status='payment_done' and user_id= " + Login.user_id;
        try {
            PreparedStatement ps = myconn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                timestamp = rs.getString("timestamp");
                // System.out.println(timestamp);
                break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        timestamp = timestamp.substring(0, 5); // convert in HH:mm
        System.out.println("Ordered at " + timestamp);
        String formattedEstimatedTime = format(estimated_time);
        System.out.println("Estimated time " + estimated_time + "minutes");

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date d1 = df.parse(formattedEstimatedTime);
            Date d2 = df.parse(timestamp);
            // System.out.println(d1.getTime()+"\n" +d2.getTime());
            long sum = d1.getTime() + d2.getTime();
            String date3 = df.format(new Date(sum));
            // System.out.println("The sum is "+date3);
            expected_time = date3;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String format(int estimated_time) {

        int hh = estimated_time / 60;
        int mm = estimated_time % 60;
        return hh + ":" + mm;
    }

    private void cancel_order() {
        String query = "update orders set order_status=\"CANCELLED\" where order_status = 'payment_done' and user_id = "
                + Login.user_id;

        PreparedStatement ps = null;
        try {
            ps = myconn.prepareStatement(query);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        display_track_menu();
    }
}
