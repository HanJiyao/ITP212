package yongrui.chatsocket;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class Chat implements Serializable {

    private String userId;
    private String channel;
    private String timeStamp;
    private String message;

    public Chat(){
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "userId='" + userId + '\'' +
                ", channel='" + channel + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
