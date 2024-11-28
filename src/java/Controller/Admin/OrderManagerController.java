package Controller.Admin;

import DAO.orderDAO;
import Entity.Order;
import Entity.OrderDetail;
import Entity.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vietth
 */
public class OrderManagerController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            String action = request.getParameter("action");
            orderDAO dao = new orderDAO();

            if (user.getIsAdmin().equalsIgnoreCase("true")) {
                if (action == null) {
                    // Hiển thị danh sách đơn hàng
                    List<Order> order = dao.getOrderInfo();
                    request.setAttribute("order", order);
                    request.getRequestDispatcher("admin/order.jsp").forward(request, response);
                } else if (action.equals("showdetail")) {
                    // Hiển thị chi tiết đơn hàng
                    String order_id = request.getParameter("order_id");
                    int id = Integer.parseInt(order_id);
                    List<OrderDetail> detail = dao.getDetail(id);
                    request.setAttribute("detail", detail);
                    request.getRequestDispatcher("admin/orderdetail.jsp").forward(request, response);
                } else if (action.equals("delete")) {
                    // Xóa đơn hàng
                    String order_id = request.getParameter("order_id");
                    int id = Integer.parseInt(order_id);
                    dao.deleteOrder(id);
                    response.sendRedirect("ordermanager"); // Chuyển hướng về trang quản lý đơn hàng
                }
            } else {
                response.sendRedirect("user?action=login");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("404.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
