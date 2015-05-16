package org.glassfish.jersey.examples.helloworld.webapp;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ltcn
 */


public class CustomerDBAgent {
    
    private static Cust_DB_Manager db;
    private String tableName = "customera";
    
    public CustomerDBAgent(){
        System.out.println(" CustomerDBAgent db "+db);
        if(db == null){
            db = new Cust_DB_Manager();
            db.open();
            System.out.println(" CustomerDBAgent db 2 "+db);
        }
    }
    public void close(){
        if(db != null){
            db.close();
            db = null;
        }
    }
    
    public boolean ifdbinit() {
        ResultSet res = db.qureyExec("select * from " +  tableName);
        if(res != null){
//            try {
////                res.close();
//            } catch (SQLException sqlExcept) {
//
//            }
            return true;
        }else{
            return false;
        }
    }
    
    public void initdb(){
        String sql = "CREATE TABLE "+ tableName + " ( "
//                + "id INT,"
                + "email CHAR(30) NOT NULL UNIQUE, "
                + "password CHAR(30)"
//                + "PRIMARY KEY (id)"
                + ")";
        try {
            db.updateExec(sql);
        } catch (SQLException sqlExcept) {
           System.out.println("initDB fail? :" + sqlExcept.getMessage());
        }
    }
    
    public String insertData(String inputemail, String inputpassword){
        String sql = "INSERT INTO " + tableName + " "
                + "(email, password) VALUES ( '"
                + inputemail + "', '" + inputpassword + "' )";
        try {
            db.updateExec(sql);
            return "OK";
        } catch (SQLException sqlE) {
            return sqlE.getMessage();
        }
    }
    
    public String changePassword(String inputemail, String inputpassword){
        String sql = "UPDATE " + tableName + " SET  "
                + "password='" + inputpassword + "' where email='"
                + inputemail + "'";
        try {
            db.updateExec(sql);
            return "OK";
        } catch (SQLException sqlE) {
            return sqlE.getMessage();
        }
    }
    
    public String deleteCustomer(String inputemail){
        String sql = "DELETE FROM " + tableName + " where email='"
                + inputemail + "'";
        try {
            db.updateExec(sql);
            return "OK";
        } catch (SQLException sqlE) {
            return sqlE.getMessage();
        }
    }
    
    public CustList<NewCustomer> getData(){
        String sql  = "SELECT * from " + tableName ;
        NewCustomer[] storagespace = new NewCustomer[64];
        CustList<NewCustomer> reslist = new CustList<NewCustomer>(storagespace,64);
        try {
            ResultSet results = db.qureyExec(sql);
            if (results == null) {
                return null;
            }
            while (results.next()) {
                //int id = results.getInt(1);
                String email = results.getString(1);
                String password = results.getString(2);
                reslist.add(new NewCustomer(email, password));
            }
            results.close();
            return reslist;
        } catch (SQLException sqlE) {
            return null;
        }
    }
}