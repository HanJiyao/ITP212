package jiyao.managedbeans;

import jiyao.ejb.RequestEJB;
import jiyao.entity.Request;
import jiyao.managedbeans.RegisterView;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Logger;

@ManagedBean
@SessionScoped
public class RequestView implements Serializable {
    @Inject
    private RequestEJB requestEJB;
    private String title;
    private String detail;
    private String location;
    private String date;
    private String time;
    private Integer price;
    private Integer duration;
    private String user;
    private static Logger log = Logger.getLogger(RegisterView.class.getName());

    public String createNewRequest() {
        log.info("Create request title : " + title);
        Request request = new Request(title,detail,location,date,time,price,duration,user);
        requestEJB.createRequest(request);
        title = null;
        detail = null;
        location = null;
        date = null;
        time = null;
        price = null;
        duration = null;
        return "/user/profile";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getPrice() { return price; }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
