package food_ordering;


public class OnlinePayment {

    protected int payment;
    protected boolean payment_done = false;

    public  OnlinePayment(int payment){
        this.payment = payment;
    }
    

    public boolean makePayment(){
        return false;
    }
    
}

//Runtime Polymorphism
//Inheritance
class UPI extends OnlinePayment{

    
    public UPI(int payment) {
        super(payment);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean makePayment() {
        
        
        System.out.println("Choose UPI to pay \n 1. Google Pay \n 2. BHIM UPI ");
        int upi_option = App.sc.nextInt();
        System.out.println("Enter your UPI ID ");
        String upi_id = App.sc.next();
        if(upi_option==1){
            payment_done = google_pay(upi_id);
        }else{
            payment_done = bhim_upi(upi_id);
        }
        
        return payment_done;        


    }

    private boolean google_pay(String upi_id){
        
        // GooglePay pay = new GooglePay();
        // payment_done = pay.Pay(upi_id, payment); 
        payment_done = true; // for now take it true always.
        return payment_done;
    }

    private boolean bhim_upi(String upi_id){
        
        // BhimPay pay = new BhimPay();
        // payment_done = pay.Pay(upi_id, payment); 
        payment_done = true; // for now take it true always.
        return payment_done;
    }
}

class NetBanking extends OnlinePayment{
    public NetBanking(int payment) {
        super(payment);
        //TODO Auto-generated constructor stub
    }
    @Override
    public boolean makePayment() {
        System.out.println("Select Bank to Procced \n 1. SBI \n 2. HDFC BANK");
        int netbaking_option = App.sc.nextInt();
        System.out.println("Enter your User ID");
        String payment_user_id = App.sc.next();
        System.out.println("Enter your Password");
        String payment_password = App.sc.next();
        if(netbaking_option==1){
           payment_done = sbi_net( payment_user_id,  payment_password);

        }else{
            payment_done = hdfc_net( payment_user_id,  payment_password);

        }

        return payment_done;

    }
    private boolean sbi_net(String payment_user_id, String payment_password){
        // SBINET pay = new SBINET();
       //  payment_done = pay.Pay(payment_user_id,payment_password, paymnet); 
       payment_done = true; // for now take it true always.
       return payment_done;
   }
   private boolean hdfc_net(String payment_user_id, String payment_password){
          // HDFCNET = new HDFCNET();
            // payment_done = pay.Pay(payment_user_id,payment_password, paymnet); 
            payment_done = true; // for now take it true always.
            return payment_done;
}
}


class Cards extends OnlinePayment{

    public Cards(int payment) {
        super(payment);
        //TODO Auto-generated constructor stub
    }

    int bank_option;
    int card_no;
    String expiry_date;
    int cvv;
    int pin;
    protected void details() {
        System.out.println("Select Bank to Procced \n 1. SBI \n 2. HDFC BANK");
        bank_option = App.sc.nextInt();
        System.out.println("Enter Your CARD NO.");
         card_no = App.sc.nextInt();
        System.out.println("Enter EXPIRY DATE in MM/YYYY Format");
         expiry_date = App.sc.next();
        System.out.println("Enter Card CVV no.");
         cvv = App.sc.nextInt();
        System.out.println("Enter Card PIN ");
         pin = App.sc.nextInt();
    }

    @Override
    public boolean makePayment() {
        // TODO Auto-generated method stub
        return super.makePayment();
    }

    


}

//MultiLevel Inheritance
class DebitCard extends Cards{

    public DebitCard(int payment) {
        super(payment);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean makePayment() {
        
       super.details();

        if(bank_option==1){
            payment_done = sbi_debit();
        }else{
            payment_done =  hdfc_debit();
        }

        return payment_done;

    }

    private boolean sbi_debit(){
        // SBIDEBIT pay = new SBIDEBIT();
       //  payment_done = pay.Pay(card_no, expiry_date, pin,cvv, payment); 
       payment_done = true; // for now take it true always.
       return payment_done;
    }

    private boolean hdfc_debit(){
        // HDFCDEBIT pay = new HDFCDEBIT();
       //  payment_done = pay.Pay(card_no, expiry_date, pin,cvv, payment); 
       payment_done = true; // for now take it true always.
       return payment_done;
    }
    
    
    

}

//MultiLevel Inheritance
class CreditCard extends Cards{

    public CreditCard(int payment) {
        super(payment);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean makePayment() {
        
       super.details();

        if(bank_option==1){
            payment_done = sbi_credit();
        }else{
            payment_done =  hdfc_credit();
        }

        return payment_done;

    }

    private boolean sbi_credit(){
        // SBICREDIT pay = new SBICREDIT();
       //  payment_done = pay.Pay(card_no, expiry_date, pin,cvv, payment); 
       payment_done = true; // for now take it true always.
       return payment_done;
    }

    private boolean hdfc_credit(){
        // HDFCCREDIT pay = new HDFCCREDIT();
       //  payment_done = pay.Pay(card_no, expiry_date, pin,cvv, payment); 
       payment_done = true; // for now take it true always.
       return payment_done;
    }
    
    
    

}

