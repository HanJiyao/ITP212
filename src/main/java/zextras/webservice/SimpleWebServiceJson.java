package zextras.webservice;

import javax.faces.bean.ManagedBean;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import java.io.StringReader;

/*@Named
@RequestScoped*/
@ManagedBean

public class SimpleWebServiceJson {

    private static final int OK_STATUS = Response.Status.OK.getStatusCode();

    private String url;
    static String jsonResponse;
    private String fromCurrency = "SGD";
    private String toCurrency = "SGD";
    private String input = "0.00";
    private String output = "0.00";
    private String rate = "1.00";

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public SimpleWebServiceJson() {
        this.url = "https://currency-api.appspot.com/api/SGD/SGD.json";
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
        SimpleWebServiceJson.jsonResponse = jsonResponse;
    }
}



