package payment;

import javax.faces.bean.ManagedBean;
import javax.persistence.*;
import java.util.Objects;
@ManagedBean

@Entity
@Table(name = "credit_card_details", schema = "itp212")
public class CreditCardDetails {
    private int id;
    private int cardNum;
    private String fullName;
    private int cvv;
    private String expiryDate;
    private int postalCode;

    public CreditCardDetails() {
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "card_num", nullable = false)
    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    @Basic
    @Column(name = "full_name", nullable = false, length = 45)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "CVV", nullable = false)
    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public CreditCardDetails(int id,int cardNum,String fullName,int cvv,
                             String expiryDate,int postalCode){
        this.id=id;
        this.cardNum=cardNum;
        this.fullName=fullName;
        this.cvv=cvv;
        this.expiryDate=expiryDate;
        this.postalCode=postalCode;
    }
    @Basic
    @Column(name = "expiry_date", nullable = false)
    public String  getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Basic
    @Column(name = "postal_code", nullable = false)
    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCardDetails that = (CreditCardDetails) o;
        return id == that.id &&
                cardNum == that.cardNum &&
                cvv == that.cvv &&
                postalCode == that.postalCode &&
                Objects.equals(fullName, that.fullName) &&
                Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNum, fullName, cvv, expiryDate, postalCode);
    }
}
