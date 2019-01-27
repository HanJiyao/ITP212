package blog;

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

public class LikesDbUtil {

    private static LikesDbUtil instance;
    private DataSource dataSource;
    private String theSearchName;
    private String jndiName = "jdbc/ITP212";

    public static LikesDbUtil getInstance() throws Exception {
        if (instance == null) {
            instance = new LikesDbUtil();
        }

        return instance;
    }

    private LikesDbUtil() throws Exception {
        dataSource = getDataSource();
    }

    private DataSource getDataSource() throws NamingException {
        Context context = new InitialContext();

        DataSource theDataSource = (DataSource) context.lookup(jndiName);

        return theDataSource;
    }

    public List<Likes> getLikes() throws Exception {

        List<Likes> likes = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from likes order by id desc";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            // process result set
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                String likeTitle = myRs.getString("likeTitle");
                int blogLikeId = myRs.getInt("blogLikeId");
                // create new student object
                Likes tempLike = new Likes(id, likeTitle, blogLikeId);

                // add it to the list of students
                likes.add(tempLike);
            }

            return likes;
        }
        finally {
            close (myConn, myStmt, myRs);
        }
    }

    public void addLikes(Likes theLike) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            String sql = "insert into likes (likeTitle, blogLikeId) values (?, ?)";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setString(1, theLike.getLikeTitle());
            myStmt.setInt(2, theLike.getBlogLikeId());

            myStmt.execute();
        }
        finally {
            close (myConn, myStmt);
        }

    }

    public Likes getLikes(int likeId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from likes where id=?";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, likeId);

            myRs = myStmt.executeQuery();

            Likes theLike = null;

            // retrieve data from result set row
            if (myRs.next()) {
                int id = myRs.getInt("id");
                String likeTitle = myRs.getString("likeTitle");
                int blogLikeId = myRs.getInt("blogLikeId");

                theLike = new Likes(id, likeTitle, blogLikeId);
            }
            else {
                throw new Exception("Could not find like id: " + likeId);
            }

            return theLike;
        }
        finally {
            close (myConn, myStmt, myRs);
        }
    }


    public void deleteLike(int likeId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            String sql = "delete from likes where id=?";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, likeId);

            myStmt.execute();
        }
        finally {
            close (myConn, myStmt);
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

    public List<Likes> searchLikes(String theSearchName)  throws Exception {

        List<Likes> likes = new ArrayList<>();

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int likeId;

        try {

            // get connection to database
            myConn = dataSource.getConnection();

            //
            // only search by name if theSearchName is not empty
            //
            if (theSearchName != null && theSearchName.trim().length() > 0) {


                // create sql to search for blog posts by title
                String sql = "select * from likes where lower(likeTitle) like ?";

                // create prepared statement
                myStmt = myConn.prepareStatement(sql);

                // set params
                String theSearchNameLike = "%" + theSearchName.toLowerCase() + "%";
                myStmt.setString(1, theSearchNameLike);


            } else {
                // create sql to get all liked posts
                String sql = "select * from likes order by id desc ";

                // create prepared statement
                myStmt = myConn.prepareStatement(sql);
            }

            // execute statement
            myRs = myStmt.executeQuery();

            // retrieve data from result set row
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                String likeTitle = myRs.getString("likeTitle");
                int blogLikeId = myRs.getInt("blogLikeId");


                // create new student object
                Likes tempLike = new Likes(id, likeTitle, blogLikeId);

                // add it to the list of Likes
                likes.add(tempLike);
            }

            return likes;
        }
        finally {
            // clean up JDBC objects
            close(myConn, myStmt, myRs);
        }
    }
}
