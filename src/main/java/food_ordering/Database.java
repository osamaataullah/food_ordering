package food_ordering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
    String dburl = "jdbc:sqlite:project.db";

    public void insert(String tablename, String[] datakey, String[] datavalue){
        

        try {
            Connection myconn = DriverManager.getConnection(dburl);

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
            Class.forName("org.sqlite.JDBC");
            Connection myconn = DriverManager.getConnection(dburl);

            String query = "SELECT * FROM "+ tablename +";";
            Statement mystmt = myconn.createStatement();
            ResultSet result = mystmt.executeQuery(query);
            System.out.println("results");
            while(result.next()){

                for(int i=0;i<datakey.length;i++){
                    if (result.getString(datakey[i]).equals(datavalue[i])){
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
}
