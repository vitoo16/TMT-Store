package DAO;

import Utils.DBUtils;
import Entity.Order;
import Entity.OrderDetail;
import Entity.Cart;
import Entity.Item;
import Entity.Product;
import Entity.User;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vietth
 */
public class orderDAO {

    // Thêm đơn hàng
    public void addOrder(User u, Cart cart, String payment) {
        LocalDate curDate = LocalDate.now();
        String date = curDate.toString();

        String insertOrderSQL = "INSERT INTO [order] (user_id, total, payment, date) VALUES (?, ?, ?, ?)";
        String selectOrderIdSQL = "SELECT TOP 1 order_id FROM [order] ORDER BY order_id DESC";
        String insertOrderDetailSQL = "INSERT INTO [order_detail] (order_id, product_id, quantity, size, color, price) VALUES (?, ?, ?, ?, ?, ?)";
        String updateProductSQL = "UPDATE product SET quantity = quantity - ? WHERE product_id = ?";

        try (Connection conn = new DBUtils().getConnection();
                PreparedStatement psOrder = conn.prepareStatement(insertOrderSQL);
                PreparedStatement psSelectOrderId = conn.prepareStatement(selectOrderIdSQL);
                PreparedStatement psInsertOrderDetail = conn.prepareStatement(insertOrderDetailSQL);
                PreparedStatement psUpdateProduct = conn.prepareStatement(updateProductSQL)) {

            // Thêm đơn hàng
            psOrder.setInt(1, u.getUser_id());
            psOrder.setBigDecimal(2, cart.getTotalMoney());
            psOrder.setString(3, payment);
            psOrder.setString(4, date);
            psOrder.executeUpdate();

            // Lấy mã đơn hàng mới nhất
            ResultSet rs = psSelectOrderId.executeQuery();
            if (rs.next()) {
                int orderId = rs.getInt(1);

                // Thêm chi tiết đơn hàng
                for (Item item : cart.getItems()) {
                    psInsertOrderDetail.setInt(1, orderId);
                    psInsertOrderDetail.setString(2, item.getProduct().getProduct_id());
                    psInsertOrderDetail.setInt(3, item.getQuantity());
                    psInsertOrderDetail.setString(4, item.getSize());
                    psInsertOrderDetail.setString(5, item.getColor());
                    psInsertOrderDetail.setBigDecimal(6, item.getProduct().getProduct_price().multiply(BigDecimal.valueOf(item.getQuantity())));
                    psInsertOrderDetail.executeUpdate();

                    // Cập nhật số lượng sản phẩm
                    psUpdateProduct.setInt(1, item.getQuantity());
                    psUpdateProduct.setString(2, item.getProduct().getProduct_id());
                    psUpdateProduct.executeUpdate();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy thông tin đơn hàng
    public List<Order> getOrderInfo() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.order_id, u.user_name, u.phone, u.address, o.date, o.total, o.payment "
                + "FROM [order] o "
                + "INNER JOIN [users] u ON o.user_id = u.user_id";
        try (Connection conn = new DBUtils().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User u = new User(
                        rs.getString("user_name"), // Tên người dùng
                        rs.getString("phone"), // Số điện thoại từ bảng Users
                        rs.getString("address") // Địa chỉ từ bảng Users
                );

                // Thêm đơn hàng vào danh sách
                list.add(new Order(
                        rs.getInt("order_id"), // ID đơn hàng
                        u, // Đối tượng User đã khởi tạo
                        rs.getBigDecimal("total"), // Tổng tiền
                        rs.getString("payment"), // Phương thức thanh toán
                        rs.getDate("date") // Ngày mua
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy đơn hàng mới nhất
    public Order getOrder() {
        String sql = "SELECT TOP 1 order_id, total, date FROM [order] ORDER BY order_id DESC";
        try (Connection conn = new DBUtils().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return new Order(rs.getInt("order_id"), rs.getBigDecimal("total"), rs.getDate("date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy chi tiết đơn hàng
    public List<OrderDetail> getDetail(int order_id) {
        List<OrderDetail> list = new ArrayList<>();
        String sql = "SELECT d.detail_id, p.product_id, p.product_name, p.img, d.quantity, d.size, d.color, d.price "
                + "FROM order_detail d "
                + "INNER JOIN product p ON d.product_id = p.product_id "
                + "WHERE d.order_id = ?";
        try (Connection conn = new DBUtils().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, order_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product(rs.getString("product_id"), rs.getString("product_name"), rs.getString("img"));
                    list.add(new OrderDetail(rs.getInt("detail_id"), p, rs.getInt("quantity"), rs.getString("size"),
                            rs.getString("color"), rs.getBigDecimal("price")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Cập nhật phương thức thanh toán
    public void updatePayment(String payment, int order_id) {
        String sql = "UPDATE [order] SET payment = ? WHERE order_id = ?";
        try (Connection conn = new DBUtils().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, payment);
            ps.setInt(2, order_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy đơn hàng theo user_id
    public List<Order> getOrderByID(int user_id) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.order_id, o.date, o.total, o.payment, u.address, u.phone "
                + "FROM [order] o "
                + "INNER JOIN [users] u ON o.user_id = u.user_id "
                + "WHERE o.user_id = ?";
        try (Connection conn = new DBUtils().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, user_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User u = new User(rs.getString("phone"), rs.getString("address"));
                    list.add(new Order(rs.getInt("order_id"), u, rs.getBigDecimal("total"), rs.getString("payment"), rs.getDate("date")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy đơn hàng theo ngày
    public List<Order> getOrderByDay() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.order_id, u.user_name, u.phone, u.address, o.date, o.total, o.payment "
                + "FROM [order] o "
                + "INNER JOIN [users] u ON o.user_id = u.user_id "
                + "WHERE CAST(o.date AS DATE) = CAST(GETDATE() AS DATE)";
        try (Connection conn = new DBUtils().getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User u = new User(
                        rs.getString("user_name"), // Tên người dùng
                        rs.getString("phone"), // Số điện thoại
                        rs.getString("address") // Địa chỉ
                );

                // Thêm đơn hàng vào danh sách
                list.add(new Order(
                        rs.getInt("order_id"), // ID đơn hàng
                        u, // Đối tượng User đã khởi tạo
                        rs.getBigDecimal("total"), // Tổng tiền
                        rs.getString("payment"), // Phương thức thanh toán
                        rs.getDate("date") // Ngày mua
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteOrder(int orderId) {
        String deleteOrderDetailSQL = "DELETE FROM order_detail WHERE order_id = ?";
        String deleteOrderSQL = "DELETE FROM [order] WHERE order_id = ?";

        try (Connection conn = new DBUtils().getConnection();
                PreparedStatement psDeleteOrderDetail = conn.prepareStatement(deleteOrderDetailSQL);
                PreparedStatement psDeleteOrder = conn.prepareStatement(deleteOrderSQL)) {

            // Xóa chi tiết đơn hàng trước
            psDeleteOrderDetail.setInt(1, orderId);
            psDeleteOrderDetail.executeUpdate();

            // Xóa đơn hàng
            psDeleteOrder.setInt(1, orderId);
            psDeleteOrder.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}
