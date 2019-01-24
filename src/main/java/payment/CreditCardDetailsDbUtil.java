package payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CreditCardDetailsDbUtil {
    private static CreditCardDetailsDbUtil instance;
    private DataSource dataSource;
    private String jndiName = "jdbc/ITP212";
    //private String jndiName = "jdbc/SQLite";
    public static CreditCardDetailsDbUtil getInstance() throws Exception {
        if (instance == null) {
            instance = new CreditCardDetailsDbUtil();
        }
        return instance;
    }

    private CreditCardDetailsDbUtil() throws Exception {
        dataSource = getDataSource();
    }

    private DataSource getDataSource() throws NamingException {
        Context context = new InitialContext();
        DataSource theDataSource = (DataSource) context.lookup(jndiName);
        return theDataSource;
    }

    public List<CreditCardDetails> getCcdetails() throws Exception {
        //students=CreditCardList
        List<CreditCardDetails> CreditCardList = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from credit_card_details  order by full_name";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            // process result set
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                int card_num = myRs.getInt("card_num");
                String fullName = myRs.getString("full_name");
                int cvv = myRs.getInt("CVV");
                String expiry_date=myRs.getString("expiry_date");
                int postal_code=myRs.getInt("postal_code");


                // create new CreditCard object
                CreditCardDetails tempCreditcard = new CreditCardDetails(id, card_num,fullName,
                        cvv,expiry_date,postal_code);

                // add it to the list of CreditCards
                CreditCardList.add(tempCreditcard);
            }

            return CreditCardList;
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void addCcdetail(CreditCardDetails theCcdetail) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            String sql = "insert into credit_card_details (id,card_num,full_name,CVV,expiry_date,postal_code) values (?,?,?,?,?,?)";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, theCcdetail.getId());
            myStmt.setInt(2, theCcdetail.getCardNum());
            myStmt.setString(3, theCcdetail.getFullName());
            myStmt.setInt(4,theCcdetail.getCvv());
            myStmt.setString(5,theCcdetail.getExpiryDate());
            myStmt.setInt(6, theCcdetail.getPostalCode());


            myStmt.execute();
        } finally {
            close(myConn, myStmt);
        }

    }

    public CreditCardDetails getCcdetail(int ccdetailId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from credit_card_details where id=?";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, ccdetailId);

            myRs = myStmt.executeQuery();

            CreditCardDetails theCcdetail = null;

            // retrieve data from result set row
            if (myRs.next()) {
                int id = myRs.getInt("id");
                int card_num = myRs.getInt("card_num");
                String full_name = myRs.getString("full_name");
                int CVV = myRs.getInt("CVV");
                String expiry_date=myRs.getString("expiry_date");
                int postal_code=myRs.getInt("postal_code");

                theCcdetail = new CreditCardDetails(id,card_num,full_name,CVV,expiry_date,postal_code);
            } else {
                throw new Exception("Could not find Credit card detail id: " + ccdetailId);
            }

            return theCcdetail;
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void updateCcdetail(CreditCardDetails theCcdetail) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            String sql = "update credit_card_details " + " set full_name=?, postal_code=?, card_num=?,CVV=?,expiry_date=?" + " where id=?";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, theCcdetail.getId());
            myStmt.setInt(2, theCcdetail.getCardNum());
            myStmt.setString(3, theCcdetail.getFullName());
            myStmt.setInt(4,theCcdetail.getCvv());
            myStmt.setString(5,theCcdetail.getExpiryDate());
            myStmt.setInt(6, theCcdetail.getPostalCode());

            myStmt.execute();
        } finally {
            close(myConn, myStmt);
        }
    }
    public void deleteCcdetail(int ccdetailId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            String sql = "delete from credit_card_details where id=?";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, ccdetailId);

            myStmt.execute();
        } finally {
            close(myConn, myStmt);
        }
    }

    private Connection getConnection() throws Exception {
        Connection theConn = dataSource.getConnection();
        return theConn;
    }

    private void close(Connection theConn, Statement theStmt) {
        close(theConn, theStmt, null);
    }

    private void close(Connection theConn, Statement theStmt, ResultSet theRs) {

        try {
            if (theRs != null) {
                theRs.close();
            }

            if (theStmt != null) {
                theStmt.close();
            }

            if (theConn != null) {
                theConn.close();
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    public List<CreditCardDetails> searchCcdetails(String theSearchCcdetail)  throws Exception {
        List<CreditCardDetails> CreditCardList = new ArrayList<>();

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int CcdetailId;

        try {

            // get connection to database
            myConn = dataSource.getConnection();

            //
            // only search by name if theSearchCcdetail is not empty
            //
            if (theSearchCcdetail != null && theSearchCcdetail.trim().length() > 0) {
                // create sql to search for students by name
                String sql = "select * from credit_card_details where lower(first_name) like ? or lower(last_name) like ?";
                // create prepared statement
                myStmt = myConn.prepareStatement(sql);
                // set params
                String theSearchCcdetailLike = "%" + theSearchCcdetail.toLowerCase() + "%";
                myStmt.setString(1, theSearchCcdetailLike);
                myStmt.setString(2, theSearchCcdetailLike);

            } else {
                // create sql to get all students
                String sql = "select * from credit_card_details order by full_name";
                // create prepared statement
                myStmt = myConn.prepareStatement(sql);
            }

            // execute statement
            myRs = myStmt.executeQuery();

            // retrieve data from result set row
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                int cardNum = myRs.getInt("card_num");
                String full_name = myRs.getString("full_name");
                int CVV = myRs.getInt("CVV");
                String expiry_date=myRs.getString("expiry_date");
                int postal_code=myRs.getInt("postal_code");

                // create new Credit card object
                CreditCardDetails tempCreditCard = new CreditCardDetails(id,cardNum,full_name,CVV,expiry_date,postal_code);

                // add it to the list of Credit card
                CreditCardList.add(tempCreditCard);
            }

            return CreditCardList;
        }
        finally {
            // clean up JDBC objects
            close(myConn, myStmt, myRs);
        }
    }
}

