package review_package;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import java.io.*;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Response;

@ManagedBean
@SessionScoped
public class ReviewController {

    private List<Review> reviews;
    private ReviewDbUtil reviewDbUtil;
    private int ratee;
    private int numrate;
    private Logger logger = Logger.getLogger(getClass().getName());

    private String theSearchName;
    private String searchUser;
    private static final int BUFFER_SIZE = 6124;
    private String folderToUpload;
    private UploadedFile uploadedFile;
    //private String folder = "..\\..\\..\\web\\resources\\pictures";



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

    public int getNumRate() throws Exception {
        numrate = reviewDbUtil.ratingNum();
        return numrate;
    }

    public int getRatee() throws Exception {
        ratee = reviewDbUtil.ratingTotal();
        return ratee;
    }

    private void addErrorMessage(Exception exc) {
        FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

//    public void reviewImage() throws Exception {
//        File uploads = new File("/path/to/uploads");
//        File file = File.createTempFile("somefilename-", ".ext", uploads);
//
//        try (InputStream input = file.getInputStream()) {
//            Files.copy(input, new File(uploads, filename).toPath());
//        }
//        catch (IOException e) {
//            // Show faces message?
//        }
//
//    }
//        public UploadedFile getUploadedFile() {
//            return uploadedFile;
//        }
//
//        public void setUploadedFile(UploadedFile uploadedFile) {
//            this.uploadedFile = uploadedFile;
//        }

//        public void saveFile(){
//            try (InputStream input = uploadedFile.getInputStream()) {
//            String fileName = uploadedFile.getSubmittedFileName();
//                Files.copy(input, new File(folder, fileName).toPath());
//            }
//            catch (Exception exc) {
//                //send this to server logs
//                logger.log(Level.SEVERE, "Error adding image", exc);
//
//                // add error message for JSF page
//                addErrorMessage(exc);
//            }
//        }

//    public void saveFile(String fileName, InputStream in, Review theReview) {
//        try {
//            // write the inputStream to a FileOutputStream
//            String username = System.getProperty("user.name");
//            String reviewPic = generateRandomHex() + fileName;
//            if (!reviewPic.contains(".jpg")) {
//                String[] temp = reviewPic.split("\\.");
//                String filename = temp[0];
//                reviewPic = filename + ".jpg";
//            }
//            theReview.setReviewPhoto(reviewPic);
//            OutputStream out = new FileOutputStream(new File("C:\\Users\\Asus\\Desktop\\PROJECT\\ITP212\\out\\artifacts\\ITP212\\resources\\pictures\\" + reviewPic));
//            int read = 0;
//            byte[] bytes = new byte[1024];
//
//            while ((read = in.read(bytes)) != -1) {
//                out.write(bytes, 0, read);
//            }
//
//            in.close();
//            out.flush();
//            out.close();
//
//            System.out.println("New file created!");
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public String generateRandomHex() {
//        Random random = new Random();
//        String new_string = "";
//
//        for (int i = 0; i < 8; i++) {
//            int val = random.nextInt();
//            String hex = new String();
//            hex = Integer.toHexString(val);
//            new_string = new_string += hex;
//        }
//
//        return new_string;
//    }

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

