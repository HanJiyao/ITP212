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
    // Java program to check if a given credit

    // Main Method
    public static void main(String[] args)
    {
        long number = 5196081888500645L;

        System.out.println(number + " is " +
                (isValid(number) ? "valid" : "invalid"));
    }

    // Return true if the card number is valid
    public static boolean isValid(long number)
    {
        return (getSize(number) >= 13 &&
                getSize(number) <= 16) &&
                (prefixMatched(number, 4) ||
                        prefixMatched(number, 5) ||
                        prefixMatched(number, 37) ||
                        prefixMatched(number, 6)) &&
                ((sumOfDoubleEvenPlace(number) +
                        sumOfOddPlace(number)) % 10 == 0);
    }

    // Get the result from Step 2
    public static int sumOfDoubleEvenPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 2; i >= 0; i -= 2)
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);

        return sum;
    }

    // Return this number if it is a single digit, otherwise,
    // return the sum of the two digits
    public static int getDigit(int number)
    {
        if (number < 9)
            return number;
        return number / 10 + number % 10;
    }

    // Return sum of odd-place digits in number
    public static int sumOfOddPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 1; i >= 0; i -= 2)
            sum += Integer.parseInt(num.charAt(i) + "");
        return sum;
    }

    // Return true if the digit d is a prefix for number
    public static boolean prefixMatched(long number, int d)
    {
        return getPrefix(number, getSize(d)) == d;
    }

    // Return the number of digits in d
    public static int getSize(long d)
    {
        String num = d + "";
        return num.length();
    }

    // Return the first k number of digits from
    // number. If the number of digits in number
    // is less than k, return number.
    public static long getPrefix(long number, int k)
    {
        if (getSize(number) > k) {
            String num = number + "";
            return Long.parseLong(num.substring(0, k));
        }
        return number;
    }
}

