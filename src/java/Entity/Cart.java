package Entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vietth
 */
public class Cart {

    List<Item> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public Cart(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    // số lượng 1 sản phẩm trong giỏ - khách sẽ mua
    public int getQuantityById(String id) {
        return getItemById(id).getQuantity();
    }

    private Item getItemById(String id) {
        for (Item i : items) {
            if (i.getProduct().getProduct_id().equals(id)) {
                return i;
            }
        }
        return null;
    }

    private Item CheckItem(String id, String size, String color) {
        for (Item i : items) {
            if (i.getProduct().getProduct_id().equals(id) && i.size.equals(size) && i.color.equals(color)) {
                return i;
            }
        }
        return null;
    }

    // add 1 sản phẩm vào giỏ, nếu có rồi thì tăng số lượng
    public void addItem(Item t) {
        if (getItemById(t.getProduct().getProduct_id()) != null && CheckItem(t.getProduct().getProduct_id(), t.size, t.color) != null) {
            Item m = getItemById(t.getProduct().getProduct_id());
            m.setQuantity(m.getQuantity() + t.getQuantity());
        } else {
            items.add(t);
        }
    }

    // loại sản phẩm khỏi giỏ
    public void removeItem(String id) {
        if (getItemById(id) != null) {
            items.remove(getItemById(id));
        }
    }

    // tổng tiền của cả giỏ hàng – sẽ add vào bảng Order
    public BigDecimal getTotalMoney() {
        BigDecimal total = BigDecimal.ZERO;
        for (Item i : items) {
            BigDecimal quantity = BigDecimal.valueOf(i.getQuantity());
            BigDecimal price = i.getProduct().getProduct_price(); // Assume product_price is BigDecimal in Product class
            total = total.add(quantity.multiply(price));
        }
        return total;
    }
}
