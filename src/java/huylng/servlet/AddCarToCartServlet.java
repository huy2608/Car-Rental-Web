/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.servlet;

import huylng.car.CarDAO;
import huylng.car.CarDTO;
import huylng.cart.CarsCart;
import huylng.user.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Shi
 */
@WebServlet(name = "AddCarToCartServlet", urlPatterns = {"/AddCarToCartServlet"})
public class AddCarToCartServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String urlRewriting = "searchPage";
        String id = request.getParameter("carId");
        String name = request.getParameter("txtName");
        String rentalDate = request.getParameter("searchRentalDate");
        String returnDate = request.getParameter("searchReturnDate");
        String category = request.getParameter("searchCategory");
        String amount = request.getParameter("searchAmount");
        boolean error = false;
        float totalPrice = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER");
            if (user != null) {
                if (returnDate.isEmpty() | rentalDate.isEmpty() | amount.isEmpty()) {
                    request.setAttribute("ERROR", "Please search before add to cart");
                    error = true;
                } else {
                    CarsCart cart = (CarsCart) session.getAttribute("CARCART");
                    if (cart == null) {
                        cart = new CarsCart();
                        session.setAttribute("RENTALDATE", rentalDate);
                        session.setAttribute("RETURNDATE", returnDate);
                    }
                    String RentalDate = (String) session.getAttribute("RENTALDATE");
                    String ReturnDate = (String) session.getAttribute("RETURNDATE");
                    if (rentalDate.compareTo(RentalDate) != 0 || returnDate.compareTo(ReturnDate) != 0) {
                        request.setAttribute("ADDERROR", "All car must have the same rentalDate and returnDate");
                        error = true;
                    } else {
                        Map<String, CarDTO> cars = cart.getCarCart();
                        CarDAO dao = new CarDAO();
                        CarDTO dto = dao.getCarByIdAndSetQuantity(id, 1);
                        if (cars == null) {
                            cars = new HashMap<>();
                        }
                        if (cars.containsKey(id)) {
                            dto.setQuantity(cars.get(id).getQuantity());
                            cart.addCarToCart(dto);
                        } else {
                            cart.addCarToCart(dto);
                        }
                        Date rent = sdf.parse(RentalDate);
                        Date returndate = sdf.parse(ReturnDate);
                        Instant start = rent.toInstant();
                        Instant end = returndate.toInstant();
                        long duration = Duration.between(start, end).toDays();
                        int rentDay = Math.round(duration);
                        session.setAttribute("RENTDATE", rentDay);
                        for (Map.Entry<String, CarDTO> entry : cart.getCarCart().entrySet()) {
                            CarDTO value = entry.getValue();
                            float price = value.getPrice() * value.getQuantity()*rentDay;
                            totalPrice = totalPrice + price;
                            session.setAttribute("TOTALPRICEOFBILL", totalPrice);
                        }
                    }
                    if (name == null) {
                        name = "";
                    }
                    session.setAttribute("CARCART", cart);
                    int currentPage = (int) session.getAttribute("CURRENTPAGE");
                    urlRewriting = "search?txtName=" + name
                            + "&rentalDate=" + rentalDate
                            + "&returnDate=" + returnDate
                            + "&cbCategory=" + category
                            + "&amount=" + amount
                            + "&page=" + currentPage;
                }
            } else {
                urlRewriting = "loginPage";
            }
        } catch (SQLException e) {
            log("Error at AddFoodToCartController_SQL: " + e.getMessage());
        } catch (NamingException e) {
            log("Error at AddFoodToCartController_Naming: " + e.getMessage());
        } catch (ParseException e) {
            log("Error at AddFoodToCartController_Parse: " + e.getMessage());
        } finally {
            if (error == true) {
                RequestDispatcher rd = request.getRequestDispatcher(urlRewriting);
                rd.forward(request, response);
            } else {
                response.sendRedirect(urlRewriting);
            }
            out.close();
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
