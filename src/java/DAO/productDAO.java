package DAO;

import Utils.DBUtils;
import Entity.Category;
import Entity.Color;
import Entity.Product;
import Entity.Size;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class productDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Lấy danh sách tất cả sản phẩm
    public List<Product> getProduct() {
        List<Product> list = new ArrayList<>();
        String sql = "select c.category_name, p.product_id, p.product_name, p.product_price, p.product_describe, p.quantity, p.img from product p inner join category c on p.category_id = c.category_id";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString(1));
                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getBigDecimal(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getProductLow() {
        List<Product> list = new ArrayList<>();
        String sql = "select c.category_name , p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img from product p inner join category c on p.category_id = c.category_id ORDER BY p.product_price ASC";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString(1));
                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getBigDecimal(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getProductHigh() {
        List<Product> list = new ArrayList<>();
        String sql = "select c.category_name , p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img from product p inner join category c on p.category_id = c.category_id ORDER BY p.product_price DESC";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString(1));
                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getBigDecimal(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getProductAZ() {
        List<Product> list = new ArrayList<>();
        String sql = "select c.category_name , p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img from product p inner join category c on p.category_id = c.category_id ORDER BY p.product_name";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString(1));
                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getBigDecimal(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getProductByCategory(int category_id) {
        List<Product> list = new ArrayList<>();
        String sql = "select c.category_name , p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img from product p inner join category c on p.category_id = c.category_id WHERE p.category_id=?";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, category_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getString(1));
                list.add(new Product(c, rs.getString(2), rs.getString(3), rs.getBigDecimal(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    // Thêm sản phẩm mới
        public void insertProduct(Product product) {
        String sql = "insert into product (product_id,category_id,product_name,product_price,product_describe,quantity,img) values(?,?,?,?,?,?,?)";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, product.getProduct_id());
            ps.setInt(2, product.getCate().getCategory_id());
            ps.setString(3, product.getProduct_name());
            ps.setBigDecimal(4, product.getProduct_price());
            ps.setString(5, product.getProduct_describe());
            ps.setInt(6, product.getQuantity());
            ps.setString(7,product.getImg());
            ps.executeUpdate();
        } catch (Exception e) {
        }
        String sql1 = "insert into product_size (product_id,size) values(?,?)";
        try {
            conn = new DBUtils().getConnection();
            for (Size i : product.getSize()) {
                ps = conn.prepareStatement(sql1);
                ps.setString(1, i.getProduct_id());
                ps.setString(2, i.getSize());
                ps.executeUpdate();
            }
        } catch (Exception e) {
        }
        String sql2 = "insert into product_color (product_id,color) values(?,?)";
        try {
            conn = new DBUtils().getConnection();
            for (Color i : product.getColor()) {
                ps = conn.prepareStatement(sql2);
                ps.setString(1, i.getProduct_id());
                ps.setString(2, i.getColor());
                ps.executeUpdate();
            }
        } catch (Exception e) {
        }
    }


// Xóa sản phẩm
    public void deleteProduct(String product_id) {
        // Xóa size trước
        String sql1 = "DELETE FROM product_size WHERE product_id=?";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql1);
            ps.setString(1, product_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Xóa color
        String sql2 = "DELETE FROM product_color WHERE product_id=?";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql2);
            ps.setString(1, product_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Xóa sản phẩm chính
        String sql3 = "DELETE FROM product WHERE product_id=?";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql3);
            ps.setString(1, product_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cập nhật sản phẩm
    public void updateProduct(Product product) {
        // Xóa size cũ
        String sql1 = "DELETE FROM product_size WHERE product_id=?";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql1);
            ps.setString(1, product.getProduct_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Xóa color cũ
        String sql2 = "DELETE FROM product_color WHERE product_id=?";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql2);
            ps.setString(1, product.getProduct_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Thêm lại size mới
        String sql3 = "INSERT INTO product_size (product_id, size) VALUES(?,?)";
        try {
            for (Size i : product.getSize()) {
                ps = conn.prepareStatement(sql3);
                ps.setString(1, i.getProduct_id());
                ps.setString(2, i.getSize());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Thêm lại color mới
        String sql4 = "INSERT INTO product_color (product_id, color) VALUES(?,?)";
        try {
            for (Color i : product.getColor()) {
                ps = conn.prepareStatement(sql4);
                ps.setString(1, i.getProduct_id());
                ps.setString(2, i.getColor());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Cập nhật thông tin sản phẩm chính
        String sql5 = "UPDATE product SET category_id=?, product_name=?, product_price=?, product_describe=?, quantity=?, img=? WHERE product_id=?";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql5);
            ps.setInt(1, product.getCate().getCategory_id());
            ps.setString(2, product.getProduct_name());
            ps.setBigDecimal(3, product.getProduct_price());
            ps.setString(4, product.getProduct_describe());
            ps.setInt(5, product.getQuantity());
            ps.setString(6, product.getImg());
            ps.setString(7, product.getProduct_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách size
    public List<Size> getSize() {
        List<Size> list = new ArrayList<>();
        String sql = "SELECT * FROM product_size";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Size(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getTop10Product() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 10 p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img FROM product p ORDER BY NEWID()";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getBigDecimal(3), rs.getString(4), rs.getInt(5), rs.getString(6)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<Product> getTrendProduct() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 5 p.product_id , p.product_name, p.product_price, p.product_describe, p.quantity,p.img FROM product p inner join order_detail bd on p.product_id = bd.product_id\n"
                + "ORDER BY bd.quantity DESC";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString(1), rs.getString(2), rs.getBigDecimal(3), rs.getString(4), rs.getInt(5), rs.getString(6)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    // Lấy danh sách color
    public List<Color> getColor() {
        List<Color> list = new ArrayList<>();
        String sql = "SELECT * FROM product_color";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Color(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy danh sách danh mục
    public List<Category> getCategory() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy sản phẩm theo ID
    public Product getProductByID(String product_id) {
        String sql = "SELECT c.category_id, c.category_name, p.product_id, p.product_name, p.product_price, p.product_describe, p.quantity, p.img FROM product p INNER JOIN category c ON p.category_id = c.category_id WHERE p.product_id=?";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, product_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category(rs.getInt(1), rs.getString(2));
                return new Product(c, rs.getString(3), rs.getString(4), rs.getBigDecimal(5), rs.getString(6), rs.getInt(7), rs.getString(8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy size theo sản phẩm
    public List<Size> getSizeByID(String product_id) {
        List<Size> list = new ArrayList<>();
        String sql = "SELECT * FROM product_size WHERE product_id=?";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, product_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Size(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy color theo sản phẩm
    public List<Color> getColorByID(String product_id) {
        List<Color> list = new ArrayList<>();
        String sql = "SELECT * FROM product_color WHERE product_id=?";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, product_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Color(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Đếm tổng số sản phẩm
    public int CountProduct() {
        int count = 0;
        String sql = "SELECT COUNT(*) as 'count' FROM product";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // Đếm số người dùng (không phải admin)
    public int CountUser() {
        int count = 0;
        String sql = "SELECT COUNT(*) as 'count' FROM users WHERE isAdmin = 'False' OR isAdmin = 'FALSE'";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // Đếm số đơn hàng
    public int CountOrder() {
        int count = 0;
        String sql = "SELECT COUNT(*) as 'count' FROM [order]";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // Đếm số sản phẩm gần hết hàng (số lượng dưới 30)
    public int CountProductLow() {
        int count = 0;
        String sql = "SELECT COUNT(*) as 'count' FROM product WHERE quantity < 30";
        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Product> getListByPage(List<Product> list,
            int start, int end) {
        ArrayList<Product> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    // Tìm kiếm sản phẩm theo từ khóa
    public List<Product> SearchAll(String text) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT DISTINCT c.category_name, p.product_id, p.product_name, p.product_price, "
                + "p.product_describe, p.quantity, p.img "
                + "FROM product p "
                + "INNER JOIN category c ON c.category_id = p.category_id "
                + "INNER JOIN product_color ps ON p.product_id = ps.product_id "
                + "WHERE p.product_name COLLATE Latin1_General_CI_AI LIKE ? "
                + "OR p.product_price LIKE ? "
                + "OR ps.color COLLATE Latin1_General_CI_AI LIKE ? "
                + "OR c.category_name COLLATE Latin1_General_CI_AI LIKE ?";

        try {
            conn = new DBUtils().getConnection();
            ps = conn.prepareStatement(sql);

            // Apply wildcards consistently
            String queryText = "%" + text + "%";
            ps.setString(1, queryText);
            ps.setString(2, queryText);
            ps.setString(3, queryText);
            ps.setString(4, queryText);

            rs = ps.executeQuery();

            while (rs.next()) {
                Category category = new Category(rs.getString("category_name"));
                Product product = new Product(
                        category,
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getBigDecimal("product_price"),
                        rs.getString("product_describe"),
                        rs.getInt("quantity"),
                        rs.getString("img")
                );
                list.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print exception for debugging
        } finally {
            // Ensure resources are closed to avoid memory leaks
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

}
