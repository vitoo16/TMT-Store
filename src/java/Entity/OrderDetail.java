/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.math.BigDecimal;

/**
 *
 * @author vietth
 */
public class OrderDetail {
    int detail_id;
    int order_id;
    Product product;
    int quantity;
    String size;
    String color;
    BigDecimal price;

    public OrderDetail() {
    }

    public OrderDetail(int detail_id, int order_id, Product product, int quantity, String size, String color) {
        this.detail_id = detail_id;
        this.order_id = order_id;
        this.product = product;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
    }
    
    public OrderDetail(int detail_id, Product product, int quantity, String size, String color, BigDecimal price) {
        this.detail_id = detail_id;
        this.product = product;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.price = price;
    }

    public int getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    
}
