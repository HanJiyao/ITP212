package items;

import entity.User;
import managedbeans.LoginView;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
            String db_url ="jdbc:mysql://localhost:3306/itp212",
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
                itemObj.setDiscount(resultSetObj.getFloat("discount"));
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
        String image = "temp";
        System.out.print(image+newItem.getName()+newItem.getDesc()+newItem.getType()+newItem.getQuantity()+newItem.getPrice()+newItem.getDiscount()+userEmail);
        try{
            pstmt = getConnection().prepareStatement("insert into items (image, name, `desc`, type, quantity, price, discount, user) values (?,?,?,?,?,?,?,?)");
            pstmt.setString(1,image);
            pstmt.setString(2,newItem.getName());
            pstmt.setString(3,newItem.getDesc());
            pstmt.setString(4,newItem.getType());
            pstmt.setInt(5,newItem.getQuantity());
            pstmt.setFloat(6,newItem.getPrice());
            pstmt.setFloat(7,newItem.getDiscount());
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
                editItem.setId(resultSetObj.getInt("id"));
                editItem.setName(resultSetObj.getString("name"));
                editItem.setType(resultSetObj.getString("type"));
                editItem.setDesc(resultSetObj.getString("desc"));
                editItem.setQuantity(resultSetObj.getInt("quantity"));
                editItem.setPrice(resultSetObj.getFloat("price"));
                editItem.setDiscount(resultSetObj.getFloat("discount"));
            }
            sessionMapObj.put("editItemObj",editItem);
            connObj.close();
        }catch (Exception sqlException){
            sqlException.printStackTrace();
        }
        return "update.xhtml?faces-redirect=true";
    }

    public static String updateItem(Item updateItem){
        String image = "temp";
        try{
            pstmt = getConnection().prepareStatement("update items set image=?, name=?,`desc`=?, type=?, quantity=?, price=?, discount=? where id=?");
            pstmt.setString(1,image);
            pstmt.setString(2,updateItem.getName());
            pstmt.setString(3,updateItem.getDesc());
            pstmt.setString(4,updateItem.getType());
            pstmt.setInt(5,updateItem.getQuantity());
            pstmt.setFloat(6,updateItem.getPrice());
            pstmt.setFloat(7,updateItem.getDiscount());
            pstmt.setInt(8,updateItem.getId());
            pstmt.executeUpdate();
            connObj.close();
        } catch (Exception sqlExcption){
            sqlExcption.printStackTrace();
        }
        return"items.xhtml?faces-redirect=true";
    }

    public static String deleteItem(int itemId){
        System.out.print("deleteItem(): Item ID: "+itemId);
        try {
            pstmt = getConnection().prepareStatement("delete from items where id ="+itemId);
            pstmt.executeUpdate();
            connObj.close();
        }catch (Exception sqlException){
            sqlException.printStackTrace();
        }
        return "/items.xhtml?faces-redirect=true";
    }
}


