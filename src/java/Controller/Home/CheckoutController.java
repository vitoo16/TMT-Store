package Controller.Home;

import DAO.orderDAO;
import Entity.Cart;
import Entity.User;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vietth
 */
public class CheckoutController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        try {

            HttpSession session = request.getSession(true);
            Cart cart = null;
            String payment = null;
            orderDAO dao = new orderDAO();
            String payment_method = request.getParameter("payment_method");

            // Kiểm tra giỏ hàng
            Object o = session.getAttribute("cart");
            if (o != null) {
                cart = (Cart) o;
            } else {
                cart = new Cart();
            }

            User acc = null;
            Object u = session.getAttribute("user");
            if (u != null) {
                if (payment_method.equals("qrcode")) {
                    payment = "QRCODE";
                }
                if (payment_method.equals("cod")) {
                    payment = "COD";
                }

                acc = (User) u;

                // Lấy thông tin phone và address từ đối tượng User
                String address = acc.getAddress();
                String phone = acc.getPhone();
                dao.addOrder(acc, cart, payment);

                session.removeAttribute("cart");
                session.setAttribute("size", 0);

                if (payment_method.equals("cod")) {
                    response.sendRedirect("home");
                }
                if (payment_method.equals("qrcode")) {
                    Entity.Order order = dao.getOrder();
                    BigDecimal total = order.getTotal().setScale(0, BigDecimal.ROUND_HALF_UP);
                    request.setAttribute("total", total);
                    request.setAttribute("order", order);
                    request.getRequestDispatcher("qrcode.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect("user?action=login");
            }
        } catch (Exception e) {
            request.getRequestDispatcher("404.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Object u = session.getAttribute("user");
        if (u != null) {
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
        } else {
            response.sendRedirect("user?action=login");
        }

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
