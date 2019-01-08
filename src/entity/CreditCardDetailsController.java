package entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
@ManagedBean
@SessionScoped
public class CreditCardDetailsController {
        private List<CreditCardDetails> ccdetails;

        private CreditCardDetailsDbUtil ccdetailsDbUtil;

        private Logger logger=Logger.getLogger(getClass().getName());
        private String theSearchCcdetail;

        public String getTheSearchCcdetail() {
            return theSearchCcdetail;
        }

        public CreditCardDetailsController() throws Exception{
            ccdetails=new ArrayList<>();
            ccdetailsDbUtil=CreditCardDetailsDbUtil.getInstance();
        }

        public List<CreditCardDetails> getCcdetails(){
            return ccdetails;
        }
        public void loadCcdetails() {
            logger.info("Loading Credit Card Details");

            logger.info("theSearchCcdetail="+theSearchCcdetail);

            try {

                if (theSearchCcdetail != null && theSearchCcdetail.trim().length() > 0) {
                    // search for ccdetails by name
                    ccdetails = ccdetailsDbUtil.searchCcdetails(theSearchCcdetail);
                }
                else {
                    // get all ccdetails from database
                    ccdetails = ccdetailsDbUtil.getCcdetails();
                }

            } catch (Exception exc) {
                // send this to server logs
                logger.log(Level.SEVERE, "Error loading ccdetails", exc);

                // add error message for JSF page
                addErrorMessage(exc);
            }
            finally {
                // reset the search info
                theSearchCcdetail = null;
            }
        }
    public String addCcdetail(CreditCardDetails theCcdetail) {

        logger.info("Adding Credit Card: " + theCcdetail);
        try {
            // add credit card to the database

            ccdetailsDbUtil.addCcdetail(theCcdetail);
        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error adding Credit Card", exc);
            // add error message for JSF page
            addErrorMessage(exc);
            return null;
        }
        return "listccdetails?faces-redirect=true";
    }
        public String loadCcdetail(int ccdetailId){
            logger.info("Loading Credit card detail:"+ccdetailId);
            try{
                //get credit card details from database
                CreditCardDetails theCcdetail=ccdetailsDbUtil.getCcdetail(ccdetailId);

                // put in the request attribute ... so we can use it on the form page
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

                Map<String, Object> requestMap = externalContext.getRequestMap();
                requestMap.put("Credit Card detail", theCcdetail);

            } catch (Exception exc) {
                // send this to server logs
                logger.log(Level.SEVERE, "Error loading credit card details id:" + ccdetailId, exc);

                // add error message for JSF page
                addErrorMessage(exc);

                return null;
            }

            return "updateCcdetails-form.xhtml";
        }

    public String updateCcdetail(CreditCardDetails theCcdetail) {

        logger.info("Updating Credit Card Information: " + theCcdetail);

        try {

            // update Credit card in the database
            ccdetailsDbUtil.updateCcdetail(theCcdetail);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error updating Credit Card info: " + theCcdetail, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "list-ccdetails?faces-redirect=true";
    }

    public String deleteCcdetail(int ccdetailId) {

        logger.info("Deleting Credit Card id: " + ccdetailId);

        try {

            // delete the credit card from the database
            ccdetailsDbUtil.deleteCcdetail(ccdetailId);

        } catch (Exception exc) {
            // send this to server logs
            logger.log(Level.SEVERE, "Error deleting creditcard detail id: " + ccdetailId, exc);

            // add error message for JSF page
            addErrorMessage(exc);

            return null;
        }

        return "list-ccdetails";
    }

    private void addErrorMessage(Exception exc) {
        FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}

