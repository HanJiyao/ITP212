package jiyao.entity;

import jiyao.orders.CartsDbUtil;

import javax.faces.bean.ManagedBean;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;
@ManagedBean
@Entity
@Table(name = "carts", schema = "itp212")
public class Cart {
    private int id;
    private int itemId;
    private float price;
    private int quantity;
    private Timestamp created;
    private int userId;
    private String image;
    private String name;
    private String type;
    private int itemQuantity;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "item_id", nullable = false)
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 2)
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Basic
    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "created", nullable = true)
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return id == cart.id &&
                itemId == cart.itemId &&
                Float.compare(cart.price, price) == 0 &&
                quantity == cart.quantity &&
                userId == cart.userId &&
                Objects.equals(created, cart.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemId, price, quantity, created, userId);
    }
    public ArrayList getUserCartList(int userId){
        return CartsDbUtil.getUserItemsList(userId);
    }

    public String createItem(int itemId, float price, int quantity, int userId){
        System.out.print("Creating cart item"+quantity);
        return CartsDbUtil.createItem(itemId, price, quantity, userId);
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
    public String deleteItem(int itemId){
        return CartsDbUtil.deleteItem(itemId);
    }

}
