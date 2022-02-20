package DBController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {

  class Holder {
     final DBConnection instance = new DBConnection();
     
     public DBConnection getInstance() {
       return instance;
     }
  }

    private final static String hostname               = "den1.mysql2.gear.host" ;
    private final static int port                     = 3306;
    private final static String userName               = "crazyrunner";
    private final static String password               = "Hp89l3-?8ncC";
    private final static String database               = "crazyrunner";

    private Connection databaseConnection = null;

    public DBConnection() {

        openConnection();
    }

    private Connection openConnection(){
        try {
        	
        	
            String url = "jdbc:mysql://"+hostname+":"+port+"/"+database;

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            databaseConnection = DriverManager.getConnection(url, userName, password);

            return databaseConnection;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public DBConnection getInstance() {
       Holder holder = new Holder();
       return holder.getInstance();
    }

    public Connection getDatabaseConnection() {
        try {
            if(!databaseConnection.isClosed()&&databaseConnection.isValid(60)){
            System.out.println("Connection Started");
               return databaseConnection;}
                
            else{
            System.out.println("Retrying Connection");
             return openConnection();}
            
               
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}