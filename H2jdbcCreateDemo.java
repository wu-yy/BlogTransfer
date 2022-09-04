import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 
import java.sql.Statement;  

public class H2jdbcCreateDemo { 
   // JDBC driver name and database URL 
   static final String JDBC_DRIVER = "org.h2.Driver";   
   static final String DB_URL = "jdbc:h2:/Users/wuyongyu/PycharmProjects/to_markdown/test";
   
   //  Database credentials 
   static final String USER = "sa"; 
   static final String PASS = ""; 
  
   public static void main(String[] args) { 
      Connection conn = null; 
      Statement stmt = null; 
      try { 
         // STEP 1: Register JDBC driver 
         Class.forName(JDBC_DRIVER); 
             
         //STEP 2: Open a connection 
         System.out.println("Connecting to database..."); 
         conn = DriverManager.getConnection(DB_URL,USER,PASS);  
         
         //STEP 3: Execute a query 
         System.out.println("Creating table in given database..."); 
         stmt = conn.createStatement(); 
         String sql =  "create table IF NOT EXISTS md( "
         + "id int primary key auto_increment,"
         + "title text," 
         + "context longtext,"
         + "create_time datetime);";  
         stmt.executeUpdate(sql);

         sql = "create table IF NOT EXISTS pic( "
         + "id int primary key auto_increment,"
         + "pname varchar(100),"
         + "path varchar(255),"
         + "create_time datetime"
         + ");";

         stmt.executeUpdate(sql);

         sql = "create table IF NOT EXISTS setting( "
         + "id int primary key auto_increment,"
         + "config_name varchar(100),"
         + "config_value varchar(100),"
         + "create_time datetime,"
         + "remark varchar(100)"
         +");";

         stmt.executeUpdate(sql);

         sql = "create table IF NOT EXISTS USER_TEMPLATE("
         +"id int primary key auto_increment,"
         + "header longtext,"
         + "bottom longtext,"
         + "create_time datetime);";

         stmt.executeUpdate(sql);


         System.out.println("Created table in given database..."); 
         
         // STEP 4: Clean-up environment 
         stmt.close(); 
         conn.close(); 
      } catch(SQLException se) { 
         //Handle errors for JDBC 
         se.printStackTrace(); 
      } catch(Exception e) { 
         //Handle errors for Class.forName 
         e.printStackTrace(); 
      } finally { 
         //finally block used to close resources 
         try{ 
            if(stmt!=null) stmt.close(); 
         } catch(SQLException se2) { 
         } // nothing we can do 
         try { 
            if(conn!=null) conn.close(); 
         } catch(SQLException se){ 
            se.printStackTrace(); 
         } //end finally try 
      } //end try 
      System.out.println("Goodbye!");
   } 
}