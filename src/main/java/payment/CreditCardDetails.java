package payment;

import javax.faces.bean.ManagedBean;
import javax.persistence.*;
import java.util.Objects;

@Entity
@ManagedBean
@Table(name = "credit_card_details", schema = "itp212")
public class CreditCardDetails {
    private int id;
    private String fullName;
    private String cardNum;
    private int cvv;
    private int postalCode;
    private String expiryDate;
    public CreditCardDetails(int id,String cardNum,String fullName,
                             int cvv,int postalCode,String expiryDate){
        this.id=id;
        this.fullName=fullName;
        this.cardNum=cardNum;
        this.cvv=cvv;
        this.postalCode=postalCode;
        this.expiryDate=expiryDate;
    }

    public CreditCardDetails() {
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "card_num")
    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    @Basic
    @Column(name = "CVV")
    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    @Basic
    @Column(name = "postal_code")
    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    @Basic
    @Column(name = "expiry_date")
    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCardDetails that = (CreditCardDetails) o;
        return id == that.id &&
                cvv == that.cvv &&
                postalCode == that.postalCode &&
                Objects.equals(fullName, that.fullName) &&
                Objects.equals(cardNum, that.cardNum) &&
                Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, cardNum, cvv, postalCode, expiryDate);
    }
}
