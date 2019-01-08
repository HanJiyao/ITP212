package review_package;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDateTime;

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
  //  private List<Float> ratingList;
    private Logger logger = Logger.getLogger(getClass().getName());

    private String theSearchName;

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

        try {

            if (theSearchName != null && theSearchName.trim().length() > 0) {
                //search for reviews by name
                reviews = reviewDbUtil.searchReviews(theSearchName);
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

//    public void ratingMoreThanFive(FacesContext context, UIComponent comp, Object value){
//
//        String rateMore = (String) value;
//
//        if (rateMore.length() > 5){
//            ((UIInput)comp).setValid(false);
//
//            FacesMessage message = new FacesMessage("Please rate from 1 to 5");
//        }
//    }

}

