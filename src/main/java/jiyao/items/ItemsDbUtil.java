package jiyao.items;

import jiyao.entity.User;
import jiyao.managedbeans.LoginView;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.ws.rs.Path;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

public class ItemsDbUtil {
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

    public static ArrayList getItemsList() {
        ArrayList itemList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj=stmtObj.executeQuery("select * from items");
            while (resultSetObj.next()){
                Item itemObj = new Item();
                itemObj.setId(resultSetObj.getInt("id"));
                itemObj.setCreated(resultSetObj.getDate("created"));
                itemObj.setDesc(resultSetObj.getString("desc"));
                itemObj.setDiscount(resultSetObj.getFloat("discount")*100);
                itemObj.setImage(resultSetObj.getString("image"));
                itemObj.setName(resultSetObj.getString("name"));
                itemObj.setPrice(resultSetObj.getFloat("price"));
                itemObj.setQuantity(resultSetObj.getInt("quantity"));
                itemObj.setType(resultSetObj.getString("type"));
                itemObj.setUser(resultSetObj.getString("user"));
                itemList.add(itemObj);
            }
            System.out.println("Total Record Fetched: "+itemList.size());
        }catch (Exception sqlException){
            sqlException.printStackTrace();
        }
        return itemList;
    }

    public static ArrayList getUserItemsList(String userEmail) {
        ArrayList itemList = new ArrayList();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj=stmtObj.executeQuery("select * from items where user =  '"+userEmail+"'");
            while (resultSetObj.next()){
                Item itemObj = new Item();
                itemObj.setId(resultSetObj.getInt("id"));
                itemObj.setCreated(resultSetObj.getDate("created"));
                itemObj.setDesc(resultSetObj.getString("desc"));
                itemObj.setDiscount(resultSetObj.getFloat("discount")*100);
                itemObj.setImage(resultSetObj.getString("image"));
                itemObj.setName(resultSetObj.getString("name"));
                itemObj.setPrice(resultSetObj.getFloat("price"));
                itemObj.setQuantity(resultSetObj.getInt("quantity"));
                itemObj.setType(resultSetObj.getString("type"));
                itemObj.setUser(resultSetObj.getString("user"));
                itemList.add(itemObj);
            }
            System.out.println("Total Record Fetched: "+itemList.size());
        }catch (Exception sqlException){
            sqlException.printStackTrace();
        }
        return itemList;
    }

    public static String createItem(Item newItem,String userEmail){
        int saveResult = 0;
        String navigationResult = "";
        System.out.print(newItem.getImage()+newItem.getName()+newItem.getDesc()+newItem.getType()+newItem.getQuantity()+newItem.getPrice()+newItem.getDiscount()+userEmail);
        try{
            pstmt = getConnection().prepareStatement("insert into items (image, name, `desc`, type, quantity, price, discount, user) values (?,?,?,?,?,?,?,?)");
            pstmt.setString(1,newItem.getImage());
            pstmt.setString(2,newItem.getName());
            pstmt.setString(3,newItem.getDesc());
            pstmt.setString(4,newItem.getType());
            pstmt.setInt(5,newItem.getQuantity());
            pstmt.setFloat(6,newItem.getPrice());
            pstmt.setFloat(7,newItem.getDiscount()/100);
            pstmt.setString(8, userEmail);
            saveResult = pstmt.executeUpdate();
            connObj.close();
        } catch (Exception sqlExcption){
            sqlExcption.printStackTrace();
        }
        if(saveResult!=0){
            navigationResult="items.xhtml?faces-redirect=true";
        }else{
            navigationResult="create.xhtml?faces-redirect=true";
        }
        return navigationResult;
    }

    public static String getItem(int itemId){
        Item viewItem = null;
        System.out.print("get(): item ID " + itemId);
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("select  * from items where id ="+itemId);
            if(resultSetObj!=null){
                resultSetObj.next();
                viewItem = new Item();
                viewItem.setImage(resultSetObj.getString("image"));
                viewItem.setId(resultSetObj.getInt("id"));
                viewItem.setName(resultSetObj.getString("name"));
                viewItem.setType(resultSetObj.getString("type"));
                viewItem.setDesc(resultSetObj.getString("desc"));
                viewItem.setQuantity(resultSetObj.getInt("quantity"));
                viewItem.setPrice(resultSetObj.getFloat("price"));
                viewItem.setDiscount(resultSetObj.getFloat("discount")*100);
                viewItem.setUser(resultSetObj.getString("user"));
            }
            sessionMapObj.put("viewItem",viewItem);
            connObj.close();
        }catch (Exception sqlException){
            sqlException.printStackTrace();
        }
        return "view.xhtml?faces-redirect=true";
    }

    public static String editItem(int itemId){
        Item editItem = null;
        System.out.print("editItem(): item ID " + itemId);
        Map<String,Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        try {
            stmtObj = getConnection().createStatement();
            resultSetObj = stmtObj.executeQuery("select  * from items where id ="+itemId);
            if(resultSetObj!=null){
                resultSetObj.next();
                editItem = new Item();
                editItem.setImage(resultSetObj.getString("image"));
                editItem.setId(resultSetObj.getInt("id"));
                editItem.setName(resultSetObj.getString("name"));
                editItem.setType(resultSetObj.getString("type"));
                editItem.setDesc(resultSetObj.getString("desc"));
                editItem.setQuantity(resultSetObj.getInt("quantity"));
                editItem.setPrice(resultSetObj.getFloat("price"));
                editItem.setDiscount(resultSetObj.getFloat("discount")*100);
            }
            sessionMapObj.put("editItemObj",editItem);
            connObj.close();
        }catch (Exception sqlException){
            sqlException.printStackTrace();
        }
        return "update.xhtml?faces-redirect=true";
    }

    public static String updateItem(Item updateItem, String imageName){
        System.out.print("update: "+imageName+" from "+updateItem.getImage());
        if(!(imageName.equals(updateItem.getImage()))){
            try{
                File file = new File("/home/han/Project/ITP212/web/resources/food/"+updateItem.getImage());
                if(file.delete()){
                    System.out.println(file.getName() + " is deleted!");
                }else{
                    System.out.println("Delete operation is failed.");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        try{
            float discount = updateItem.getDiscount()/100;
            pstmt = getConnection().prepareStatement("update items set image=?, name=?,`desc`=?, type=?, quantity=?, price=?, discount=? where id=?");
            pstmt.setString(1,imageName);
            pstmt.setString(2,updateItem.getName());
            pstmt.setString(3,updateItem.getDesc());
            pstmt.setString(4,updateItem.getType());
            pstmt.setInt(5,updateItem.getQuantity());
            pstmt.setFloat(6,updateItem.getPrice());
            pstmt.setFloat(7,discount);
            pstmt.setInt(8,updateItem.getId());
            pstmt.executeUpdate();
            connObj.close();
        } catch (Exception sqlExcption){
            sqlExcption.printStackTrace();
        }
        return"items.xhtml?faces-redirect=true";
    }

    public static String deleteItem(int itemId, String imageName){
        System.out.print("deleteItem(): Item ID: "+itemId);
        try {
            pstmt = getConnection().prepareStatement("delete from items where id ="+itemId);
            pstmt.executeUpdate();
            connObj.close();
        }catch (Exception sqlException){
            sqlException.printStackTrace();
        }
        try{
            File file = new File("/home/han/Project/ITP212/web/resources/food/"+imageName);
            if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
            }else{
                System.out.println("Delete operation is failed.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "/items.xhtml?faces-redirect=true";
    }
}


