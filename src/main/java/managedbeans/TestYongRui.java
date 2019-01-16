package managedbeans;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class TestYongRui {
    private int id;
    private String firstInput;
    private String secondInput;

    public TestYongRui() {
        System.out.println("testing constructor");
    }

    public TestYongRui(int id, String firstInput, String secondInput) {
        this.id = id;
        this.firstInput = firstInput;
        this.secondInput = secondInput;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstInput() {
        return firstInput;
    }

    public void setFirstInput(String firstInput) {
        this.firstInput = firstInput;
    }

    public String getSecondInput() {
        return secondInput;
    }

    public void setSecondInput(String secondInput) {
        this.secondInput = secondInput;
    }

    @Override
    public String toString() {
        return "TestYongRui{" +
                "id=" + id +
                ", firstInput='" + firstInput + '\'' +
                ", secondInput='" + secondInput + '\'' +
                '}';
    }
}
