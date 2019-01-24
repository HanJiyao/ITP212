package main.java.review_package;
import org.primefaces.model.UploadedFile;

import javax.faces.bean.ManagedBean;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;

@ManagedBean
public class Review {

    private int id;
    private int reviewUId;
    private String displayName;
    private String reviewTitle;
    private String reviewText;
    private int rating;
    private Date reviewDate;
    private Time reviewTime;
    private String reviewPhoto;

    public Review(){
    }
    public Review(int id, int reviewUId, String displayName, String reviewTitle, String reviewText, int rating, Date reviewDate, Time reviewTime, String reviewPhoto) {
        this.id = id;
        this.reviewUId = reviewUId;
        this.displayName = displayName;
        this.reviewTitle = reviewTitle;
        this.reviewText = reviewText;
        this.rating = rating;
        this.reviewDate = reviewDate;
        this.reviewTime = reviewTime;
        this.reviewPhoto = reviewPhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReviewUId() {
        return reviewUId;
    }

    public void setReviewUId(int reviewUId) {
        this.reviewUId = reviewUId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Time getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Time reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getReviewPhoto() {
        return reviewPhoto;
    }

    public void setReviewPhoto(String reviewPhoto) {
        this.reviewPhoto = reviewPhoto;
    }

    @Override
    public String toString() {
        return "Review [id=" + id + ", review user Id=" + reviewUId + "Display Name=" + displayName + ", Review="
                + reviewText + ", rating=" + rating + ", Review Date= " + reviewDate + ", Review Time= " + reviewTime + "]";
    }
}
