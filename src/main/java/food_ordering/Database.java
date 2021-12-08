package food_ordering;

import java.sql.*;

public class Database {
    private static String dburl = "jdbc:sqlite:project.db";
    private static Connection conn = null;

    //Use this function only to get connection object
    public static Connection connector(){
        try {
            if(conn == null) {
                Class.forName("org.sqlite.JDBC");
                conn = (Connection) DriverManager.getConnection(dburl);
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insert(String tablename, String[] datakey, String[] datavalue){
        try {
            //Class.forName("org.sqlite.JDBC");
            Connection myconn = Database.connector();

            String query = "INSERT INTO "+tablename+"( ";
            for(int i=0;i<datakey.length; i++){
                if(i==(datakey.length-1)){
                    query += datakey[i] + " ";
                }else{
                query += datakey[i] + ", ";
                }
            }
            query += " ) VALUES ( ";
            for(int i=0;i<datakey.length; i++){
                if(i==(datakey.length-1)){
                    query += '"' + datavalue[i] + '"' + " ";
                }else{
                query += '"' + datavalue[i] + '"' + ", ";
                }
            }
            query += " );";
            System.out.println(query);

            PreparedStatement mystmt = myconn.prepareStatement(query);
            mystmt.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Connection Successfull");
    }

    public Boolean verifyData(String tablename, String[] datakey, String[] datavalue){
        boolean res = false;
        try {
            //Class.forName("org.sqlite.JDBC");
            Connection myconn = Database.connector();

            String query = "SELECT * FROM "+ tablename +";";
            Statement mystmt = myconn.createStatement();
            ResultSet result = mystmt.executeQuery(query);
            System.out.println("results");
            while(result.next()){

                for(int i=0;i<datakey.length;i++){
                    if (result.getString(datakey[i]).equals(datavalue[i])){
                        Login.user_id = result.getInt("Id");
                        res = true;
                    }else{
                        res = false;
                        break;
                    }
                }
                if(res){break;}
            }



        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }
        System.out.println("Connection Successfull");
        return res;
        
    }

    public String getUserCity(int user_id) {
        try {
            //Class.forName("org.sqlite.JDBC");
            Connection myconn = Database.connector();
            String query = "SELECT city FROM user where id = " + user_id + ";";
            Statement mystmt = myconn.createStatement();
            ResultSet rs = mystmt.executeQuery(query);
            return rs.getString("city");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Error in retrieving city...");
        return "";
    }

}
