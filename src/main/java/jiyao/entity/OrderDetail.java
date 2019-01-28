package jiyao.entity;

import javax.faces.bean.ManagedBean;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@ManagedBean
@Entity
@Table(name = "order_details", schema = "itp212")
public class OrderDetail {
    private int id;
    private int orderId;
    private int itemId;
    private int sellerId;
    private int quantity;
    private float price;
    private Timestamp created;
    private String deliveryStatus;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "order_id", nullable = false)
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
    @Column(name = "seller_id", nullable = false)
    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
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
    @Column(name = "price", nullable = false, precision = 2)
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Basic
    @Column(name = "created", nullable = false)
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Basic
    @Column(name = "delivery_status", nullable = false, length = 255)
    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return id == that.id &&
                orderId == that.orderId &&
                itemId == that.itemId &&
                sellerId == that.sellerId &&
                quantity == that.quantity &&
                Float.compare(that.price, price) == 0 &&
                Objects.equals(created, that.created) &&
                Objects.equals(deliveryStatus, that.deliveryStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, itemId, sellerId, quantity, price, created, deliveryStatus);
    }
}
