package blog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BlogDbUtil {

    private static BlogDbUtil instance;
    private DataSource dataSource;
    private String theSearchName;
    private String jndiName = "jdbc/ITP212";

    public static BlogDbUtil getInstance() throws Exception {
        if (instance == null) {
            instance = new BlogDbUtil();
        }

        return instance;
    }

    private BlogDbUtil() throws Exception {
        dataSource = getDataSource();
    }

    private DataSource getDataSource() throws NamingException {
        Context context = new InitialContext();

        DataSource theDataSource = (DataSource) context.lookup(jndiName);

        return theDataSource;
    }

    public List<Blog> getBlogs() throws Exception {

        List<Blog> blogs = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from blog order by blogDate desc";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            // process result set
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                String blogTitle = myRs.getString("blogTitle");
                String blogContent = myRs.getString("blogContent");
                String blogCategory = myRs.getString("blogCategory");
                Date blogDate = myRs.getDate("blogDate");
                String blogPoster = myRs.getString("blogPoster");

                // create new student object
               Blog tempBlog = new Blog(id, blogTitle, blogContent, blogCategory, blogDate, blogPoster);

                // add it to the list of students
                blogs.add(tempBlog);
            }

            return blogs;
        }
        finally {
            close (myConn, myStmt, myRs);
        }
    }

    public void addBlog(Blog theBlog) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            String sql = "insert into blog (blogTitle, blogContent, blogCategory, blogPoster) values (?, ?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setString(1, theBlog.getBlogTitle());
            myStmt.setString(2, theBlog.getBlogContent());
            myStmt.setString(3, theBlog.getBlogCategory());
            myStmt.setString(4, theBlog.getBlogPoster());

            myStmt.execute();
        }
        finally {
            close (myConn, myStmt);
        }

    }

    public Blog getBlog(int blogId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from blog where id=? order by blogDate desc";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, blogId);

            myRs = myStmt.executeQuery();

            Blog theBlog = null;

            // retrieve data from result set row
            if (myRs.next()) {
                int id = myRs.getInt("id");
                String blogTitle = myRs.getString("blogTitle");
                String blogContent = myRs.getString("blogContent");
                String blogCategory = myRs.getString("blogCategory");
                Date blogDate = myRs.getDate("blogDate");
                String blogPoster = myRs.getString("blogPoster");

                theBlog = new Blog(id, blogTitle, blogContent, blogCategory, blogDate, blogPoster);
            }
            else {
                throw new Exception("Could not find blog id: " + blogId);
            }

            return theBlog;
        }
        finally {
            close (myConn, myStmt, myRs);
        }
    }

    public void updateBlog(Blog theBlog) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            String sql = "update blog "
                    + " set blogTitle=?, blogContent=?, blogPoster=?"
                    + " where id=?";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setString(1, theBlog.getBlogTitle());
            myStmt.setString(2, theBlog.getBlogContent());
            myStmt.setString(3, theBlog.getBlogPoster());
            myStmt.setInt(4, theBlog.getId());

            myStmt.execute();
        }
        finally {
            close (myConn, myStmt);
        }

    }

    public void deleteBlog(int blogId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            String sql = "delete from blog where id=?";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, blogId);

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

    public List<Blog> searchBlogs(String theSearchName)  throws Exception {

        List<Blog> blogs = new ArrayList<>();

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int blogId;

        try {

            // get connection to database
            myConn = dataSource.getConnection();

            //
            // only search by name if theSearchName is not empty
            //
            if (theSearchName != null && theSearchName.trim().length() > 0) {


                // create sql to search for blog posts by title
                String sql = "select * from blog where lower(blogTitle) like ?";

                // create prepared statement
                myStmt = myConn.prepareStatement(sql);

                // set params
                String theSearchNameLike = "%" + theSearchName.toLowerCase() + "%";
                myStmt.setString(1, theSearchNameLike);


            } else {
                // create sql to get all students
                String sql = "select * from blog order by id desc ";

                // create prepared statement
                myStmt = myConn.prepareStatement(sql);
            }

            // execute statement
            myRs = myStmt.executeQuery();

            // retrieve data from result set row
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                String blogContent = myRs.getString("blogContent");
                String blogTitle = myRs.getString("blogTitle");
                String blogCategory = myRs.getString("blogCategory");
                Date blogDate = myRs.getDate("blogDate");
                String blogPoster = myRs.getString("blogPoster");


                // create new student object
                Blog tempBlog = new Blog(id, blogTitle, blogContent, blogCategory, blogDate, blogPoster);

                // add it to the list of students
                blogs.add(tempBlog);
            }

            return blogs;
        }
        finally {
            // clean up JDBC objects
            close(myConn, myStmt, myRs);
        }
    }
}
