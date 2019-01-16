package review_package;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ReviewController {

    private List<Review> reviews;
    private ReviewDbUtil reviewDbUtil;
    private int ratee;
    private Logger logger = Logger.getLogger(getClass().getName());

    private String theSearchName;
    private String searchUser;
    private static final int BUFFER_SIZE = 6124;
    private String folderToUpload;

    public ReviewController() throws Exception {
        reviews = new ArrayList<>();

        reviewDbUtil = ReviewDbUtil.getInstance();
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void loadReviews() {
        logger.info("Loading reviews");

        logger.info("theSearchName = " + theSearchName);

        logger.info("searchUser = " + searchUser);

        try {

            if (theSearchName != null && theSearchName.trim().length() > 0) {
                //search for reviews by name
                reviews = reviewDbUtil.searchReviews(theSearchName);
            }

            else if (searchUser != null && searchUser.trim().length() > 0) {
                //search for reviews by name
                reviews = reviewDbUtil.searchReviewsName(searchUser);
            }

            else {
                //get all reviews from database
                reviews = reviewDbUtil.getReviews();
            }
        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error loading reviews", exc);

            //add error message for JSF page
            addErrorMessage(exc);
        }
        finally {
            //reset the search info
            theSearchName = null;
            searchUser = null;
        }
    }

    public String addReview(Review theReview){

        logger.info("Adding review: " + theReview);

        try {

            // add review to the database
            reviewDbUtil.addReview(theReview);

        } catch (Exception exc) {
            //send this to server logs
            logger.log(Level.SEVERE, "Error adding reviews", exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "review?faces-redirect=true";
    }

    public String loadReview(int reviewId){

        logger.info("loading review: " + reviewId);

        try{
            //get review from database
            Review theReview = reviewDbUtil.getReview(reviewId);

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

            Map<String, Object> requestMap = externalContext.getRequestMap();
            requestMap.put("review", theReview);
        } catch (Exception exc){
            //send this to server logs
            logger.log(Level.SEVERE, "Error loading review id: " + reviewId, exc);

            addErrorMessage(exc);

            return null;
        }

        return  "update-review.xhtml";
    }

    public String updateReview(Review theReview) {

        logger.info("updating review: " + theReview);

        try {
            //update review in the database
            reviewDbUtil.updateReview(theReview);

        } catch (Exception exc) {
            //send this to server logs
            logger.log(Level.SEVERE, "Error updating review: " + theReview, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "review?faces-redirect=true";
    }

    public String deleteReview(int reviewId) {

        logger.info("Deleting review id: " + reviewId);
    //    reviews.remove(reviewId);
        try {

            //delete the review
            reviewDbUtil.deleteReview(reviewId);

        } catch (Exception exc) {
            logger.log(Level.SEVERE, "Error deleting review id: " + reviewId, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

//        if (reviews.contains(reviewId)){
//            reviews.remove(reviewId);
 //       }
        return "review?faces-redirect=true";
    }

//    public void addPhoto(FileUploadEvent event) {
//
//        ExternalContext extContext =
//                FacesContext.getCurrentInstance().getExternalContext();
//        File result = new File(extContext.getRealPath
//                ("//WEB-INF//files//" + event.getFile().getFileName()));
//        System.out.println(extContext.getRealPath
//                ("//WEB-INF//files//" + event.getFile().getFileName()));
//
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(result);
//
//            byte[] buffer = new byte[BUFFER_SIZE];
//
//            int bulk;
//            InputStream inputStream = event.getFile().getInputstream();
//            while (true) {
//                bulk = inputStream.read(buffer);
//                if (bulk < 0) {
//                    break;
//                }
//                fileOutputStream.write(buffer, 0, bulk);
//                fileOutputStream.flush();
//            }
//
//            fileOutputStream.close();
//            inputStream.close();
//
//            FacesMessage msg =
//                    new FacesMessage("File Description", "file name: " +
//                            event.getFile().getFileName() + "<br/>file size: " +
//                            event.getFile().getSize() / 1024 +
//                            " Kb<br/>content type: " +
//                            event.getFile().getContentType() +
//                            "<br/><br/>The file was uploaded.");
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//
//            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                    "The files were not uploaded!", "");
//            FacesContext.getCurrentInstance().addMessage(null, error);
//        }
//}

    public int getRatee() throws Exception {
        ratee = reviewDbUtil.ratingTotal();
        return ratee;
    }

    private void addErrorMessage(Exception exc) {
        FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getTheSearchName() {
        return theSearchName;
    }

    public void setTheSearchName(String theSearchName) {
        this.theSearchName = theSearchName;
    }

    public String getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(String searchUser) {
        this.searchUser = searchUser;
    }

}

