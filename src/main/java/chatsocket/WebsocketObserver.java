package chatsocket;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.event.WebsocketEvent;
import javax.faces.event.WebsocketEvent.Closed;
import javax.faces.event.WebsocketEvent.Opened;
import java.util.logging.Level;
import java.util.logging.Logger;


@ApplicationScoped
public class WebsocketObserver {


    private static final Logger LOG = Logger.getLogger(WebsocketObserver.class.getName());

    public void onOpen(@Observes @Opened WebsocketEvent event) {
        LOG.log(Level.INFO, "event opend::{0}", event);
    }

    public void onClose(@Observes @Closed WebsocketEvent closed) {
        LOG.log(Level.INFO, "event closed::{0}", closed);
    }

}
