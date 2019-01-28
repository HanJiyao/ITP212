package jiyao.orders;

import jiyao.entity.Cart;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class CartsDbUtil {
    public static Statement stmtObj;
    public static Connection connObj;
    public static ResultSet resultSetObj;
    public static PreparedStatement pstmt;
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String db_url ="jdbc:mysql://localhost:3306/itp212?autoReconnect=true&useSSL=false",
                    db_userName = "root",
                    db_password = "mysql";
            connObj = DriverManager.getConnection(db_url,db_userName,db_password);
        } catch(Exception sqlException) {
            sqlException.printStackTrace();
        }
        return connObj;
    }

    public static ArrayList getUserItemsList(int userId) {
        ArrayList cartList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj=stmtObj.executeQuery("select * from carts join items on carts.item_id=items.id where user_id =  '"+userId+"'");
            while (resultSetObj.next()){
                Cart cartObj = new Cart();
                cartObj.setImage(resultSetObj.getString("image"));
                cartObj.setName(resultSetObj.getString("name"));
                cartObj.setId(resultSetObj.getInt("id"));
                cartObj.setItemId(resultSetObj.getInt("item_id"));
                cartObj.setCreated(resultSetObj.getTimestamp("created"));
                cartObj.setPrice(resultSetObj.getFloat("price"));
                cartObj.setQuantity(resultSetObj.getInt("carts.quantity"));
                cartObj.setItemQuantity(resultSetObj.getInt("items.quantity"));
                cartObj.setType(resultSetObj.getString("type"));
                cartList.add(cartObj);
            }
            System.out.println("Total Record Fetched: "+cartList.size());
        }catch (Exception sqlException){
            sqlException.printStackTrace();
        }
        return cartList;
    }
    public static String createItem(int itemId, float price, int quantity, int userId){
        int saveResult = 0;
        String navigationResult = "";
        try{
            System.out.print("new cart"+itemId+price+"quantity"+quantity+userId);
            pstmt = getConnection().prepareStatement("insert into carts (item_id, price, quantity, user_id) VALUES (?,?,?,?)");
            pstmt.setInt(1,itemId);
            pstmt.setFloat(2,price);
            pstmt.setInt(3, quantity);
            pstmt.setInt(4, userId);
            saveResult = pstmt.executeUpdate();
            connObj.close();
        } catch (Exception sqlExcption){
            sqlExcption.printStackTrace();
        }
        if(saveResult!=0){
            navigationResult="../cart.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }
    public static String deleteItem(int itemId){
        System.out.print("deleteItem(): Item ID: "+itemId);
        try {
            pstmt = getConnection().prepareStatement("delete from carts where id ="+itemId);
            pstmt.executeUpdate();
            connObj.close();
        }catch (Exception sqlException){
            sqlException.printStackTrace();
        }
        return "/cart.xhtml?faces-redirect=true";
    }
}
