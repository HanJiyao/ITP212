package example;

import javax.faces.bean.ManagedBean;
import java.util.Arrays;
import java.util.List;

@ManagedBean
public class currency {

    private List<String> types = Arrays.asList("SGD","USD","CNY","EUR","JPY","AUD");

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}