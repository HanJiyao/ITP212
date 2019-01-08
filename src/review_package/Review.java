package review_package;
import javax.faces.bean.ManagedBean;
import java.time.LocalDateTime;
import java.sql.Timestamp;

@ManagedBean
public class Review {

    private int id;
    private int reviewUId;
    private String displayName;
    private String reviewText;
    private int rating;
//    private Timestamp date;

    public Review(){
    }
    public Review(int id, int reviewUId, String displayName, String reviewText, int rating) {
        this.id = id;
        this.reviewUId = reviewUId;
        this.displayName = displayName;
        this.reviewText = reviewText;
        this.rating = rating;
    //    this.date = date;
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

//    public void setDate(Timestamp date) {
//        this.date = date;
//    }

    @Override
    public String toString() {
        return "Review [id=" + id + ", review user Id=" + reviewUId + "Display Name=" + displayName + ", Review="
                + reviewText + ", rating=" + rating + "]";
    }
}
