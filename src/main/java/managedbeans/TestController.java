package managedbeans;

import com.sun.org.apache.xpath.internal.operations.Bool;
import entity.User;

import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@ManagedBean
@SessionScoped
public class TestController {

    private Logger logger = Logger.getLogger(getClass().getName());
    private String testInputWithoutObject;

    public String getTestInputWithoutObject() {
        return testInputWithoutObject;
    }

    public void setTestInputWithoutObject(String testInputWithoutObject) {
        this.testInputWithoutObject = testInputWithoutObject;
    }

    public void testInputMethod(TestYongRui theTestBoi) {
        //logger.warning("testing");
        //logger.severe("input test without object is " + testInputWithoutObject);
        logger.severe(theTestBoi.toString());
        setTestInputWithoutObject("helloTEST");
    }
    private String bar = "bar from FooBeanZZZ";

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    private int year;

    public void beanMethod() {
        // OR - retrieve values inside beanMethod
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        //String model1 = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("model");
        //String year1 = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("year");
        //TODO https://stackoverflow.com/questions/7221495/pass-parameter-to-premotecommand-from-javascript
        String modelTest = params.get("model");
        model = modelTest;
        year = Integer.parseInt(params.get("year"));
        logger.severe("model test --> " + modelTest);
        logger.severe("model actual managed propety --> " + model);
        logger.severe("year --> " + String.valueOf(year));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Executed",
                "Using RemoteCommand with parameters model := " + modelTest + ", year := " + year));
        User usergg = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("User");
        logger.severe("USER --> ");
        logger.severe(usergg.toString());

    }

}
