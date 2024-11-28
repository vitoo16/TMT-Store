package Controller.Admin;

import DAO.productDAO;
import Entity.Category;
import Entity.Color;
import Entity.Product;
import Entity.Size;
import Entity.User;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author vietth
 */
public class ProductManagerController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user != null && user.getIsAdmin().equalsIgnoreCase("true")) {
                productDAO dao = new productDAO();

                // Display product list
                if (action == null || action.equals("")) {
                    List<Product> product = dao.getProduct();
                    List<Size> size = dao.getSize();
                    List<Color> color = dao.getColor();
                    List<Category> category = dao.getCategory();

                    request.setAttribute("CategoryData", category);
                    request.setAttribute("ProductData", product);
                    request.setAttribute("SizeData", size);
                    request.setAttribute("ColorData", color);

                    request.getRequestDispatcher("/admin/product.jsp").forward(request, response);
                }

                // Navigate to product insertion page
                if (action.equalsIgnoreCase("insert")) {
                    List<Category> category = dao.getCategory();
                    request.setAttribute("CategoryData", category);
                    request.getRequestDispatcher("/admin/productinsert.jsp").forward(request, response);
                }

                // Handle product insertion
                if (action.equalsIgnoreCase("insertproduct")) {
                    String product_id = request.getParameter("product_id");
                    String category_id = request.getParameter("category_id");
                    String product_name = request.getParameter("product_name");
                    String product_price = request.getParameter("product_price");
                    String product_size = request.getParameter("product_size");
                    String product_color = request.getParameter("product_color");
                    String product_quantity = request.getParameter("product_quantity");
                    String product_img = request.getParameter("product_img");
                    String product_describe = request.getParameter("product_describe");

                    product_id = (product_id == null || product_id.isEmpty()) ? "NEW" : product_id;
                    category_id = (category_id == null || category_id.isEmpty()) ? "1" : category_id;
                    product_name = (product_name == null || product_name.isEmpty()) ? "NEW" : product_name;
                    product_price = (product_price == null || product_price.isEmpty()) ? "0" : product_price;
                    product_size = (product_size == null || product_size.isEmpty()) ? "" : product_size;
                    product_color = (product_color == null || product_color.isEmpty()) ? "" : product_color;
                    product_quantity = (product_quantity == null || product_quantity.isEmpty()) ? "0" : product_quantity;
                    product_img = (product_img == null || product_img.isEmpty()) ? "default.jpg" : product_img;
                    product_describe = (product_describe == null || product_describe.isEmpty()) ? "No description" : product_describe;

                    int quantity = Integer.parseInt(product_quantity);
                    BigDecimal price = new BigDecimal(product_price);
                    int cid = Integer.parseInt(category_id);

                    Category cate = new Category(cid);

                    String[] sizeArray = product_size.split("\\s*,\\s*");
                    String[] colorArray = product_color.split("\\s*,\\s*");

                    List<Size> sizeList = new ArrayList<>();
                    for (String s : sizeArray) {
                        sizeList.add(new Size(product_id, s));
                    }

                    List<Color> colorList = new ArrayList<>();
                    for (String c : colorArray) {
                        colorList.add(new Color(product_id, c));
                    }

                    Product product = new Product();
                    product.setCate(cate);
                    product.setProduct_id(product_id);
                    product.setProduct_name(product_name);
                    product.setProduct_price(price);
                    product.setProduct_describe(product_describe);
                    product.setQuantity(quantity);
                    product.setImg(product_img);
                    product.setSize(sizeList);
                    product.setColor(colorList);
                    productDAO dao1 = new productDAO();
                    dao1.insertProduct(product);

                    response.sendRedirect("productmanager");
                }

                // Handle product update
                if (action.equalsIgnoreCase("updateproduct")) {
                    String product_id = request.getParameter("product_id");
                    String category_id = request.getParameter("category_id");
                    String product_name = request.getParameter("product_name");
                    String product_price = request.getParameter("product_price");
                    String product_size = request.getParameter("product_size");
                    String product_color = request.getParameter("product_color");
                    String product_quantity = request.getParameter("product_quantity");
                    String product_img = "images/" + request.getParameter("product_img");
                    String product_describe = request.getParameter("product_describe");

                    int quantity = Integer.parseInt(product_quantity);
                    BigDecimal price = new BigDecimal(product_price);
                    int cid = Integer.parseInt(category_id);

                    Category cate = new Category(cid);

                    String[] sizeArray = product_size.split("\\s*,\\s*");
                    String[] colorArray = product_color.split("\\s*,\\s*");

                    List<Size> sizeList = new ArrayList<>();
                    for (String s : sizeArray) {
                        sizeList.add(new Size(product_id, s));
                    }

                    List<Color> colorList = new ArrayList<>();
                    for (String c : colorArray) {
                        colorList.add(new Color(product_id, c));
                    }

                    Product product = new Product();
                    product.setCate(cate);
                    product.setProduct_id(product_id);
                    product.setProduct_name(product_name);
                    product.setProduct_price(price);
                    product.setProduct_describe(product_describe);
                    product.setQuantity(quantity);
                    product.setImg(product_img);
                    product.setSize(sizeList);
                    product.setColor(colorList);

                    dao.updateProduct(product);

                    response.sendRedirect("productmanager");
                }

                // Handle product deletion
                if (action.equalsIgnoreCase("deleteproduct")) {
                    String product_id = request.getParameter("product_id");
                    dao.deleteProduct(product_id);
                    response.sendRedirect("productmanager");
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
        return "Product management servlet";
    }
}
