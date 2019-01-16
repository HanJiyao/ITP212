package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name= "requests")
public class Request implements Serializable {
    private int id;
    private String title;
    private String detail;
    private String location;
    private String date;
    private String time;
    private Integer price;
    private Integer duration;
    private String user;

    public Request() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 32)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "detail", nullable = true, length = 255)
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Basic
    @Column(name = "location", nullable = true, length = 255)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "date", nullable = true, length = 32)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Basic
    @Column(name = "time", nullable = true, length = 32)
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Basic
    @Column(name = "price", nullable = true)
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Basic
    @Column(name = "duration", nullable = true)
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "user", nullable = true, length = 255)
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id == request.id &&
                Objects.equals(title, request.title) &&
                Objects.equals(detail, request.detail) &&
                Objects.equals(location, request.location) &&
                Objects.equals(date, request.date) &&
                Objects.equals(time, request.time) &&
                Objects.equals(price, request.price) &&
                Objects.equals(duration, request.duration) &&
                Objects.equals(user, request.user);
    }
    public Request(String title, String detail, String location, String date, String time, Integer price, Integer duration, String user){
        this.title = title;
        this.time = time;
        this.detail = detail;
        this.location = location;
        this.date = date;
        this.time = time;
        this.price = price;
        this.duration = duration;
        this.user = user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, detail, location, date, time, price, duration, user);
    }
}
