package org.primefaces.showcase;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.xml.bind.ValidationException;

// card is valid or not.
import java.util.Scanner;


// Java program to check if a given credit
// card is valid or not.
import java.util.Scanner;

@ManagedBean
public class cheee_validatepaymentdetails {

    private String firstName;
    private String lastName;
    private String phoneNumber;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void validateTheDate(FacesContext context,
                                UIComponent component,
                                Object value) throws ValidationException, ValidatorException {
        if (value==null){
            return;
        }
        String data = value.toString();
        if(!data.startsWith("LUV")){
            FacesMessage message = new FacesMessage("Course cide must start with LUV");
            throw new ValidatorException(message);
        }

    }

}

