package payment;

import javax.faces.bean.ManagedBean;
import javax.persistence.*;
import java.util.Objects;
import java.util.Scanner;


@Entity
@ManagedBean
@javax.annotation.ManagedBean
@Table(name = "credit_card_details", schema = "itp212")
public class CreditCardDetails {
    private int id;
    private String fullName;
    private String cardNum;
    private int cvv;
    private int postalCode;
    private String expiryDate;
    private int balance;
    private int topupamount;
    private int newbalance;
    public int test=50;
    public CreditCardDetails(String fullName,String cardNum,
                             int cvv,String expiryDate,int postalCode,int balance,int id){
        this.balance=balance;
        this.id=id;
        this.fullName=fullName;
        this.cardNum=cardNum;
        this.cvv=cvv;
        this.postalCode=postalCode;
        this.expiryDate=expiryDate;

    }

    public int topUpBalance(){
        balance=balance+50;
        return balance;
    }

    public int getTopupamount() {
        return topupamount;
    }


    public void setTopupamount(int topupamount) {
        this.topupamount = topupamount;
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

    @Basic
    @Column(name = "balance")
    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCardDetails that = (CreditCardDetails) o;
        return id == that.id &&
                cvv == that.cvv &&
                postalCode == that.postalCode &&
                balance==that.balance &&
                Objects.equals(fullName, that.fullName) &&
                Objects.equals(cardNum, that.cardNum) &&
                Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,balance, fullName, cardNum, cvv, postalCode, expiryDate);
    }

    }



