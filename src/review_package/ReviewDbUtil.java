package review_package;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Timestamp;

public class ReviewDbUtil {

    private static ReviewDbUtil instance;
    private DataSource dataSource;
    private String jndiName = "jdbc/ITP212";

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
                String reviewText = myRs.getString("reviewText");
                int rating = myRs.getInt("rating");
 //               Timestamp date = myRs.getTimestamp("date");

                // create new review object
                Review tempReview = new Review(id, reviewUId, displayName, reviewText, rating);

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
            myConn = getConnection();

            String sql = "insert into review (reviewUId, displayName, reviewText, rating) values (?, ?, ?, ?)";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, theReview.getReviewUId());
            myStmt.setString(2, theReview.getDisplayName());
            myStmt.setString(3, theReview.getReviewText());
            myStmt.setInt(4, theReview.getRating());

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
                String reviewText = myRs.getString("reviewText");
                int rating = myRs.getInt("rating");
//                Timestamp date = myRs.getTimestamp("date");

                theReview = new Review(id, reviewUId, displayName, reviewText, rating);
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
                    + " set reviewUId=?, displayName=?, reviewText=?, rating=?, date=NOW()"
                    + " where id=?";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, theReview.getReviewUId());
            myStmt.setString(2, theReview.getDisplayName());
            myStmt.setString(3, theReview.getReviewText());
            myStmt.setInt(4, theReview.getRating());
            myStmt.setInt(5, theReview.getId());

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
    public List<Review> searchReviews(String theSearchName)  throws Exception {
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
                String sql = "select * from review where lower(reviewText) like ?";
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
                String reviewText = myRs.getString("reviewText");
//                Timestamp date = myRs.getTimestamp("date");

                // create new review object
                Review tempReview = new Review(id, reviewUId, displayName, reviewText, rating);

                // add it to the list of reviews
                reviews.add(tempReview);
            }

            return reviews;
        } finally {
            // clean up JDBC objects
            close(myConn, myStmt, myRs);
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
//
//                    // retrieve data from result set row
//
//
//                    // create new review object
////                    rate.add(ratingList);
////                    if(!rate.isEmpty()){
////                        for (Float rate1 : rate){
////                            ratingttl += rate1;
////                        }
////                        return ratingttl / rate.size();
////                    }
//                }
                    //               ratingttl = ratingsum / .size();

                     rateSum = rates;

                }
            } finally {
                close(myConn, myStmt, myRs);
            }
            return rateSum;

    }
}

