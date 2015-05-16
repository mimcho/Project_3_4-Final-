package org.glassfish.jersey.examples.helloworld.webapp;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ltcn
 */


public  class Cust_DB_Manager {
    //private String dbURL = "jdbc:mysql://localhost/test?user=root";
    private String dbURL = "jdbc:derby://localhost:1527/sample;user=app;password=app";
   // private static String dbURL = "jdbc:derby://localhost:1527/myDB;create=true;user=me;password=mine";
    
    
    
    
    private Connection conn;
    private Statement stmt;
    
    public Cust_DB_Manager(){
        open();
    }
    
    public void open(){
        try {
           // Class.forName("com.mysql.jdbc.Driver").newInstance();
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            conn = DriverManager.getConnection(dbURL);
            stmt = conn.createStatement();
          
            System.out.println(" Cust_DB_Manager conn -> "+conn);
        } catch (Exception ex) {
            System.out.println("Connect fail: " + ex.toString());
          
        }
    }
    
    public void close(){
        try
        {
            if (stmt != null)
            {
                stmt.close();
            }
            if (conn != null)
            {
                DriverManager.getConnection(dbURL + ";shutdown=true");
                conn.close();
            }           
        }
        catch (SQLException sqlExcept)
        {
            System.out.println("close fail? :"+ sqlExcept.toString());
        }

    }
    
    public ResultSet qureyExec(String sql) {
        try {
            if(stmt == null){
                stmt = conn.createStatement();
            }
            ResultSet res = stmt.executeQuery(sql);
            return res;
        }
        catch (SQLException sqlExcept){
            System.out.println("qureyExec fail? :" + sqlExcept.toString());
            return null;
        }
    }
    
    public void updateExec(String sql) throws SQLException{

            if(stmt == null){
                stmt = conn.createStatement();
            }
            stmt.executeUpdate(sql);
            
        

    }
    
    
    
}