package food_ordering;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoPayment {

    public void doPayment(int bill) {

        // Online payment class object
       
            OnlinePayment userPayment = new OnlinePayment(bill);

            System.out.println(
                    "Select Payment Mode to continue for online payment\n 1. UPI \n 2. NETBANKING \n 3. DEBIT CARD \n 4. CREDIT CARD ");

            int onlinePaymentOption = App.sc.nextInt();

            boolean payment_done = false;
            if (onlinePaymentOption == 1) {
                UPI userUPI = new UPI(userPayment.payment);
                payment_done = userUPI.makePayment();
                verifyPayment(payment_done);

            } else if (onlinePaymentOption == 2) {
                NetBanking userNetBanking = new NetBanking(userPayment.payment);
                payment_done = userNetBanking.makePayment();
                verifyPayment(payment_done);

            } else if (onlinePaymentOption == 3) {
                DebitCard user_debit = new DebitCard(userPayment.payment);
                payment_done = user_debit.makePayment();
                verifyPayment(payment_done);

            } else {
                CreditCard user_credit = new CreditCard(userPayment.payment);
                payment_done = user_credit.makePayment();
                verifyPayment(payment_done);

            }
         
    }

    private void verifyPayment(boolean payment_done) {
        /*function to verify the payment*/
        System.out.println("Processing Your Payment...");
        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (payment_done) {

            Connection myconn = Database.connector();
            String query = "UPDATE orders set order_status = 'payment_done' where order_status = 'payamount'  AND user_id="
                    + Login.user_id;
            try {
                PreparedStatement myqueryps = myconn.prepareStatement(query);
                myqueryps.execute();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("Payment Done \n Thank you For Your Order.");
            Track t = new Track();
            t.display_track_menu();
        }
    }

}
