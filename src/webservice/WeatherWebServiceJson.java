package webservice;

import javax.faces.bean.ManagedBean;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import java.io.StringReader;
import java.util.Map;

/*@Named
@RequestScoped*/
@ManagedBean

public class WeatherWebServiceJson {

	private static final int OK_STATUS = Response.Status.OK.getStatusCode();
	private String url;
	private String jsonResponse;


	public WeatherWebServiceJson(){
		this.url = "https://api.data.gov.sg/v1/environment/psi";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getJsonResponse() {
		return jsonResponse;
	}

	public void setJsonResponse(String jsonResponse) {
		this.jsonResponse = jsonResponse;
	}

	public void callWebService(){
		// call the service and get the response object
		Response response = ClientBuilder.newClient()
				.target(this.url)
				.request(MediaType.APPLICATION_JSON)
				.get();

		// process the response object
		StatusType status = response.getStatusInfo();
		int statusCode = status.getStatusCode();
		if (statusCode == OK_STATUS) {
			System.out.println("Status is ok!!");
			this.jsonResponse = response.readEntity(String.class);
			//this.parseJson(this.jsonResponse);
		} else {
			System.out.printf("Service returned status: \"%d %s\"\n",
					statusCode, status.getReasonPhrase());
		}
	}
}
