package managedbeans;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.User;
import org.primefaces.json.JSONObject;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TestSQL {

    private static TestSQL instance;
    private DataSource dataSource;
    private String jndiName = "jdbc/ITP212";

    private Logger logger = Logger.getLogger(getClass().getName());

    public static TestSQL getInstance() throws Exception {
        if (instance == null) {
            instance = new TestSQL();
        }
        return instance;
    }

    private TestSQL() throws Exception {
        dataSource = getDataSource();
    }

    private DataSource getDataSource() throws NamingException {
        Context context = new InitialContext();
        DataSource theDataSource = (DataSource) context.lookup(jndiName);
        return theDataSource;
    }

    public void getStudents() throws Exception {

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        List<User> allUSERS = new ArrayList<>();

        try {
            myConn = getConnection();

            String sql = "select * from users";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            // process result set
            while (myRs.next()) {

                // retrieve data from result set row
                String email = myRs.getString("email");
                String password = myRs.getString("password");
                String name = myRs.getString("name");
                // create new student object
                User tempStudent = new User(email, password, name);
                ObjectMapper mapper = new ObjectMapper();
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tempStudent));
                // add it to the list of students
                allUSERS.add(tempStudent);
                String jsonString = new JSONObject()
                        .put("JSON1", email)
                        .put("JSON2", password)
                        .put("JSON3", name).toString();

                System.out.println(jsonString);
            }

            logger.severe("ALL USERS COMING SOON" + allUSERS);
            logger.severe(allUSERS.toString());
            ObjectMapper mapper = new ObjectMapper();
            logger.severe("Last");
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allUSERS));

        }
        finally {
            close (myConn, myStmt, myRs);
        }
    } //end of getStudents()

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
}
