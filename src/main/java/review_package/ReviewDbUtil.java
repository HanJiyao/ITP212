package main.java.review_package;

import org.primefaces.model.UploadedFile;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ReviewDbUtil {

    private static ReviewDbUtil instance;
    private DataSource dataSource;
    private String jndiName = "jdbc/ITP212";
    private File filename;


    public static ReviewDbUtil getInstance() throws Exception {
        if (instance == null) {
            instance = new ReviewDbUtil();
        }

        return instance;
    }

    private ReviewDbUtil() throws Exception {
        dataSource = getDataSource();
    }

    private DataSource getDataSource() throws NamingException {
        Context context = new InitialContext();

        DataSource theDataSource = (DataSource) context.lookup(jndiName);

        return theDataSource;
    }

    public List<Review> getReviews() throws Exception {

        List<Review> reviews = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from review order by rating DESC";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            // process result set
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                int reviewUId = myRs.getInt("reviewUId");
                String displayName = myRs.getString("displayName");
                String reviewTitle = myRs.getString("reviewTitle");
                String reviewText = myRs.getString("reviewText");
                int rating = myRs.getInt("rating");
                Date reviewDate = myRs.getDate("reviewDate");
                Time reviewTime = myRs.getTime("reviewDate");
                String reviewPhoto = myRs.getString("reviewPhoto");

                // create new review object
                Review tempReview = new Review(id, reviewUId, displayName, reviewTitle, reviewText, rating, reviewDate, reviewTime, reviewPhoto);

                // add it to the list of students
                reviews.add(tempReview);
            }
            return reviews;

        }
        finally {
            close(myConn, myStmt, myRs);
        }

    }

    public void addReview(Review theReview) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // Load driver
            Class.forName("com.mysql.jdbc.Driver");
            // Connect to the database
            myConn = getConnection();

            String sql = "insert into review (reviewUId, displayName, reviewTitle, reviewText, rating, reviewPhoto) values (?, ?, ?, ?, ?, ?)";
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, theReview.getReviewUId());
            myStmt.setString(2, theReview.getDisplayName());
            myStmt.setString(3, theReview.getReviewTitle());
            myStmt.setString(4, theReview.getReviewText());
            myStmt.setInt(5, theReview.getRating());
            myStmt.setString(6, theReview.getReviewPhoto());

            myStmt.execute();


    }
        finally {
            close (myConn, myStmt);
        }

    }

    public Review getReview(int reviewId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from review where id=? order by rating";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, reviewId);

            myRs = myStmt.executeQuery();

            Review theReview = null;

            // retrieve data from result set row
            if (myRs.next()) {
                int id = myRs.getInt("id");
                int reviewUId = myRs.getInt("reviewUId");
                String displayName = myRs.getString("displayName");
                String reviewTitle = myRs.getString("reviewTitle");
                String reviewText = myRs.getString("reviewText");
                int rating = myRs.getInt("rating");
                Date reviewDate = myRs.getDate("reviewDate");
                Time reviewTime = myRs.getTime("reviewDate");
                String reviewPhoto = myRs.getString("reviewPhoto");

                theReview = new Review(id, reviewUId, displayName, reviewTitle, reviewText, rating, reviewDate, reviewTime, reviewPhoto);
            }
            else {
                throw new Exception("Could not find review id: " + reviewId);
            }

            return theReview;
        }
        finally {
            close (myConn, myStmt, myRs);
        }
    }

    public void updateReview(Review theReview) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            String sql = "update review"
                    + " set reviewUId=?, displayName=?, reviewTitle=?, reviewText=?, rating=?, reviewPhoto=?"
                    + " where id=?";

            myStmt = myConn.prepareStatement(sql);

            //read the file
//            File file = filename;
//            FileInputStream input = new FileInputStream(file);

            // set params
            myStmt.setInt(1, theReview.getReviewUId());
            myStmt.setString(2, theReview.getDisplayName());
            myStmt.setString(3, theReview.getReviewTitle());
            myStmt.setString(4, theReview.getReviewText());
            myStmt.setInt(5, theReview.getRating());
            myStmt.setString(6, theReview.getReviewPhoto());
            myStmt.setInt(7, theReview.getId());

            myStmt.execute();
        }
        finally {
            close (myConn, myStmt);
        }

    }

    public void deleteReview(int reviewId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            String sql = "delete from review where id=?";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, reviewId);

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
    public List<Review> searchReviews(String theSearchName) throws Exception {
        List<Review> reviews = new ArrayList<>();

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int reviewId;

        try {

            // get connection to database
            myConn = dataSource.getConnection();

            //
            // only search by name if theSearchName is not empty
            //
            if (theSearchName != null && theSearchName.trim().length() > 0) {
                // create sql to search for students by name
                String sql = "select * from review where lower(reviewText) OR lower(displayName) like ?";
                // create prepared statement
                myStmt = myConn.prepareStatement(sql);
                // set params
                String theSearchNameLike = "%" + theSearchName.toLowerCase() + "%";
                myStmt.setString(1, theSearchNameLike);

            } else {
                // create sql to get all reviews
                String sql = "select * from review order by reviewText";
                // create prepared statement
                myStmt = myConn.prepareStatement(sql);
            }

            // execute statement
            myRs = myStmt.executeQuery();

            // retrieve data from result set row
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                int reviewUId = myRs.getInt("reviewUId");
                String displayName = myRs.getString("displayName");
                int rating = myRs.getInt("rating");
                String reviewTitle = myRs.getString("reviewTitle");
                String reviewText = myRs.getString("reviewText");
                Date reviewDate = myRs.getDate("reviewDate");
                Time reviewTime = myRs.getTime("reviewDate");
                String reviewPhoto = myRs.getString("reviewPhoto");

                // create new review object
                Review tempReview = new Review(id, reviewUId, displayName, reviewTitle, reviewText, rating, reviewDate, reviewTime, reviewPhoto);

                // add it to the list of reviews
                reviews.add(tempReview);
            }

            return reviews;

        } finally {
            // clean up JDBC objects
            close(myConn, myStmt, myRs);
        }
    }

    public List<Review> searchReviewsName(String searchUser) throws Exception {
        List<Review> reviews = new ArrayList<>();

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        int reviewId;

        try {

            // get connection to database
            myConn = dataSource.getConnection();

            //
            // only search by name if theSearchName is not empty
            //
            if (searchUser != null && searchUser.trim().length() > 0) {
                // create sql to search for students by name
                String sql = "select * from review where lower(displayName) like ?";
                // create prepared statement
                myStmt = myConn.prepareStatement(sql);
                // set params
                String searchUserLike = "%" + searchUser.toLowerCase() + "%";
                myStmt.setString(1, searchUserLike);

            } else {
                // create sql to get all reviews
                String sql = "select * from review order by displayName";
                // create prepared statement
                myStmt = myConn.prepareStatement(sql);
            }

            // execute statement
            myRs = myStmt.executeQuery();

            // retrieve data from result set row
            while (myRs.next()) {

                // retrieve data from result set row
                int id = myRs.getInt("id");
                int reviewUId = myRs.getInt("reviewUId");
                int rating = myRs.getInt("rating");
                String displayName = myRs.getString("displayName");
                String reviewTitle = myRs.getString("reviewTitle");
                String reviewText = myRs.getString("reviewText");
//                Timestamp date = myRs.getTimestamp("date");
                Date reviewDate = myRs.getDate("reviewDate");
                Time reviewTime = myRs.getTime("reviewDate");
                String reviewPhoto = myRs.getString("reviewPhoto");

                // create new review object
                Review tempReview = new Review(id, reviewUId, displayName, reviewTitle, reviewText, rating, reviewDate, reviewTime, reviewPhoto);

                // add it to the list of reviews
                reviews.add(tempReview);
            }

            return reviews;

        } finally {
            // clean up JDBC objects
            close(myConn, myStmt, myRs);

            //clear search bar
        }
    }
        public int ratingTotal() throws Exception {

            //    List<Float> ratingList = new ArrayList<>();
            int rateSum = 0;

            Connection myConn = null;
            Statement myStmt = null;
            ResultSet myRs = null;

            try {
                myConn = getConnection();

                String sql = "select avg(rating) as rateSum from review";

                myStmt = myConn.createStatement();

                myRs = myStmt.executeQuery(sql);

                // process result set
                while (myRs.next()) {
                    int rates = myRs.getInt("rateSum");
                   rateSum = rates;

                }
            } finally {
                close(myConn, myStmt, myRs);
            }
            return rateSum;
    }
}

