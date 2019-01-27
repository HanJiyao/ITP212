package blog;

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
public class LikesController {

    private List<Likes> likes;
    private LikesDbUtil likesDbUtil;
    private Logger logger = Logger.getLogger(getClass().getName());

    private String theSearchName;

    public LikesController() throws Exception {
        likes = new ArrayList<>();

        likesDbUtil = LikesDbUtil.getInstance();
    }

    public List<Likes> getLikes() {
        return likes;
    }

    public void loadLikes() {

        logger.info("Loading likes");

        logger.info("theSearchName = " + theSearchName);

        try {

            if (theSearchName != null && theSearchName.trim().length() > 0) {
                // search for students by name
                likes = likesDbUtil.searchLikes(theSearchName);
            }
            else {
                // get all students from database
                likes = likesDbUtil.getLikes();
            }

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error loading likes", exc);

            // add error message for JSF page
            addErrorMessage(exc);
        }
        finally {
            // reset the search info
            theSearchName = null;
        }
    }

    public String addLikes(Likes theLike) {

        logger.info("Adding like: " + theLike);

        try {


            likesDbUtil.addLikes(theLike);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error adding likes", exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "list-blogs?faces-redirect=true";
    }

    public String loadLikes(int likeId) {

        logger.info("loading likes: " + likeId);

        try {
            // get student from database
            Likes theLike = likesDbUtil.getLikes(likeId);

            // put in the request attribute ... so we can use it on the form page
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

            Map<String, Object> requestMap = externalContext.getRequestMap();
            requestMap.put("like", theLike);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error loading like id:" + likeId, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "update-blog.xhtml";
    }



    public String deleteLike(int likeId) {

        logger.info("Deleting like id: " + likeId);

        try {

            // delete the student from the database
            likesDbUtil.deleteLike(likeId);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error deleting like id: " + likeId, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "list-blogs?faces-redirect=true";
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

}
