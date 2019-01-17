package items;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.Date;

@ManagedBean
public class Item {
    private int id;
    private String image;
    private String name;
    private String desc;
    private String type;
    private float price;
    private int quantity;
    private float discount;
    private String user;
    private Date created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    };

    public ArrayList itemList;

    @PostConstruct
    public void init(){
        itemList = ItemsDbUtil.getItemsList();
    }
    public ArrayList getItemList(){
        return itemList;
    }
    public String createItem(Item newItemObj,String userEmail){
        return ItemsDbUtil.createItem(newItemObj,userEmail);
    }
    public String editItem(int itemId){
        return ItemsDbUtil.editItem(itemId);
    }
    public String updateItem(Item updateItemObj){
        return ItemsDbUtil.updateItem(updateItemObj);
    }
    public String deleteItem(int itemId){
        return ItemsDbUtil.deleteItem(itemId);
    }
}
