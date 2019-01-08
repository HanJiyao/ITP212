import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;


//Defines the base URI for all resource URIs.
@ApplicationPath("/webservices")
//The java class declares root resource and provider classes
public class MyWebServices extends ResourceConfig {

    public MyWebServices (){
        packages("example;webservice");
    }

}