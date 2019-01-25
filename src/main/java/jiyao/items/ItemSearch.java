package jiyao.items;

import review_package.Review;

import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
public class ItemSearch {
    private Float min = 0f;
    private Float max = 99999f;
    private String keyword;
    private String type;
    private int size = 0;

    public Float getMin() {
        return min;
    }

    public void setMin(Float min) {
        this.min = min;
    }

    public Float getMax() {
        return max;
    }

    public void setMax(Float max) {
        this.max = max;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Item> getItemsList() throws Exception {
        List<Item> itemArrayList = new ArrayList<>();
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("jdbc/ITP212");
            myConn = dataSource.getConnection();
            String sql = "select * from items";
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(sql);
            while (myRs.next()) {
                Item itemObj = new Item();
                itemObj.setId(myRs.getInt("id"));
                itemObj.setCreated(myRs.getDate("created"));
                itemObj.setDesc(myRs.getString("desc"));
                itemObj.setDiscount(myRs.getFloat("discount")*100);
                itemObj.setImage(myRs.getString("image"));
                itemObj.setName(myRs.getString("name"));
                itemObj.setPrice(myRs.getFloat("price"));
                itemObj.setQuantity(myRs.getInt("quantity"));
                itemObj.setType(myRs.getString("type"));
                itemObj.setUser(myRs.getString("user"));
                itemArrayList.add(itemObj);
            }
            return itemArrayList;
        }
        finally {
            close(myConn, myStmt, myRs);
        }

    }
    private void close(Connection theConn, Statement theStmt) {
        close(theConn, theStmt, null);
    }
    private void close(Connection theConn, Statement theStmt, ResultSet theRs) {
        try {
            if (theRs != null) { theRs.close(); }
            if (theStmt != null) { theStmt.close(); }
            if (theConn != null) { theConn.close(); }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
