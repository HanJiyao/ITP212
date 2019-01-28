package jiyao.entity;

import javax.faces.bean.ManagedBean;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
@ManagedBean
@Entity
@Table(name = "orders", schema = "itp212")
public class Order {
    private int id;
    private Timestamp orderDate;
    private int buyerId;
    private float totalPrice;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "order_date", nullable = true)
    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    @Basic
    @Column(name = "buyer_id", nullable = false)
    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    @Basic
    @Column(name = "total_price", nullable = false, precision = 2)
    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                buyerId == order.buyerId &&
                Float.compare(order.totalPrice, totalPrice) == 0 &&
                Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate, buyerId, totalPrice);
    }
}
