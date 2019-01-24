package chatsocket;

import javax.faces.bean.ManagedBean;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;


@ManagedBean
public class ChatServer implements Serializable {

    private static final Logger LOG = Logger.getLogger(ChatServer.class.getName());
    private static SimpleDateFormat timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    @Inject
    @Push
    PushContext userChannel;

    @Inject
    @Push
    PushContext applicationChannel;


    public void pushUserChannel(Chat chat) {
        //userChannel.send("[z" +  timeStamp.format(new Date()) + "] " + chat.getUserId() + " : " + chat.getMessage(),
        //    chat.getChannel());
        userChannel.send("{\"userId\":" + chat.getUserId() + "}",chat.getChannel());
        //userChannel.send("{\"userId\":" + '"' + chat.getMessage() +'"' + "}",chat.getChannel());
        //TODO read https://medium.com/mindorks/json-parse-and-json-stringify-in-javascript-4de609c19d46
        // https://stackoverflow.com/questions/18910939/how-to-get-json-key-and-value-in-javascript
        // TODO BUILD JSON IN JAVA https://stackoverflow.com/questions/8876089/how-to-fluently-build-json-in-java
        LOG.severe("Chat object --> " + chat.toString() + "\nchat user id = " + chat.getUserId() + "\nchat msg = " + chat.getMessage());
        chat.setMessage("");
    }

    public void pushApplicationChannel(Chat chat) {
        applicationChannel.send("[" +  timeStamp.format(new Date()) + "] Admin : "+ chat.getMessage());
        chat.setMessage("");
    }

    public void pushMultiUsersChannel() {
        userChannel.send("sent to userChannel at ::" + LocalDateTime.now(), Arrays.asList("channel1", "channel2"));
    }
}
