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
public class BlogController {

    private List<Blog> blogs;
    private BlogDbUtil blogDbUtil;
    private Logger logger = Logger.getLogger(getClass().getName());

    private String theSearchName;

    public BlogController() throws Exception {
        blogs = new ArrayList<>();

        blogDbUtil = BlogDbUtil.getInstance();
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void loadBlogs() {

        logger.info("Loading blogs");

        logger.info("theSearchName = " + theSearchName);

        try {

            if (theSearchName != null && theSearchName.trim().length() > 0) {
                // search for students by name
                blogs = blogDbUtil.searchBlogs(theSearchName);
            }
            else {
                // get all students from database
                blogs = blogDbUtil.getBlogs();
            }

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error loading blogs", exc);

            // add error message for JSF page
            addErrorMessage(exc);
        }
        finally {
            // reset the search info
            theSearchName = null;
        }
    }

    public String addBlog(Blog theBlog) {

        logger.info("Adding blog: " + theBlog);

        try {

            // add student to the database
            blogDbUtil.addBlog(theBlog);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error adding blogs", exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "list-blogs?faces-redirect=true";
    }

    public String loadBlog(int blogId) {

        logger.info("loading blog: " + blogId);

        try {
            // get student from database
            Blog theBlog = blogDbUtil.getBlog(blogId);

            // put in the request attribute ... so we can use it on the form page
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

            Map<String, Object> requestMap = externalContext.getRequestMap();
            requestMap.put("blog", theBlog);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error loading blog id:" + blogId, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "update-blog.xhtml";
    }

    public String updateBlog(Blog theBlog) {

        logger.info("updating blog: " + theBlog);

        try {

            // update student in the database
            blogDbUtil.updateBlog(theBlog);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error updating blog: " + theBlog, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "list-blogs?faces-redirect=true";
    }

    public String deleteBlog(int blogId) {

        logger.info("Deleting blog id: " + blogId);

        try {

            // delete the student from the database
            blogDbUtil.deleteBlog(blogId);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error deleting blog id: " + blogId, exc);

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
