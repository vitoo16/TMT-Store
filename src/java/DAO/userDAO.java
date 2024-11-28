package DAO;

import Utils.DBUtils;
import Entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vietth
 */
public class userDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Kiểm tra người dùng dựa trên email và mật khẩu
    public User checkUser(String user_email, String user_pass) {
        String query = "SELECT * FROM users WHERE user_email = ? AND user_pass = ?";
        try (Connection conn = new DBUtils().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user_email);
            ps.setString(2, user_pass);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("user_id"), 
                                    rs.getString("user_name"), 
                                    rs.getString("user_email"), 
                                    rs.getString("user_pass"), 
                                    rs.getString("isAdmin"), 
                                    rs.getString("phone"), 
                                    rs.getString("address"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật tất cả thông tin người dùng: tên, mật khẩu, số điện thoại, địa chỉ
    public void updateUser(int user_id, String user_name, String user_pass, String phone, String address) {
        String sql = "UPDATE users SET user_name = ?, user_pass = ?, phone = ?, address = ? WHERE user_id = ?";
        try (Connection conn = new DBUtils().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user_name);
            ps.setString(2, user_pass);
            ps.setString(3, phone);
            ps.setString(4, address);
            ps.setInt(5, user_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Kiểm tra người dùng qua email
    public User checkAcc(String user_email) {
        String query = "SELECT * FROM users WHERE user_email = ?";
        try (Connection conn = new DBUtils().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user_email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("user_id"), 
                                    rs.getString("user_name"), 
                                    rs.getString("user_email"), 
                                    rs.getString("user_pass"), 
                                    rs.getString("isAdmin"), 
                                    rs.getString("phone"), 
                                    rs.getString("address"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Đăng ký tài khoản mới
    public void signup(String user_email, String user_pass) {
        String query = "INSERT INTO users (user_name, user_email, user_pass, isAdmin) VALUES (?, ?, ?, ?)";
        try (Connection conn = new DBUtils().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, "");  // Để trống tên khi đăng ký
            ps.setString(2, user_email);
            ps.setString(3, user_pass);
            ps.setString(4, "False");  // Mặc định không phải admin
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách tất cả người dùng
    public List<User> getUser() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = new DBUtils().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new User(rs.getInt("user_id"), 
                                  rs.getString("user_name"), 
                                  rs.getString("user_email"), 
                                  rs.getString("user_pass"), 
                                  rs.getString("isAdmin"), 
                                  rs.getString("phone"), 
                                  rs.getString("address")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Cấp quyền admin cho người dùng
    public void setAdmin(int user_id, String isAdmin) {
        String sql = "UPDATE users SET isAdmin = ? WHERE user_id = ?";
        try (Connection conn = new DBUtils().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, isAdmin.toUpperCase());
            ps.setInt(2, user_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
