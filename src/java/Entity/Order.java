package Entity;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author vietth
 */
public class Order {

    int order_id;
    User user;
    BigDecimal total;
    String payment;
    Date date;

    public Order() {
    }

    // Constructor đầy đủ
    public Order(int orderId, User user, BigDecimal total, String payment, Date date) {
        this.order_id = orderId;
        this.user = user;
        this.total = total;
        this.payment = payment;
        this.date = date;

    }

    public Order(int order_id, BigDecimal total, String payment, Date date) {
        this.order_id = order_id;
        this.total = total;
        this.payment = payment;
        this.date = date;
    }

    // Constructor dùng khi chỉ có order_id, total, và date (không có user)
    public Order(int order_id, BigDecimal total, Date date) {
        this.order_id = order_id;
        this.total = total;
        this.date = date;
    }

    // Getter và Setter cho các thuộc tính
    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
