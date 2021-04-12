/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.servlet;

import huylng.bill.BillDAO;
import huylng.billdetail.BillDetailDAO;
import huylng.car.CarDTO;
import huylng.cart.CarsCart;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

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
        boolean error = false;
        String url = "viewCartPage";
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                CarsCart cart = (CarsCart) session.getAttribute("CARCART");
                if (cart != null) {
                    Map<String, CarDTO> cars = cart.getCarCart();
                    UUID billId = UUID.randomUUID();
                    BillDAO billdao = new BillDAO();
                    String username = (String) session.getAttribute("USERNAME");
                    String discountCode = (String) session.getAttribute("DISCOUNTCODE");
                    float totalPrice = (float) session.getAttribute("TOTALPRICEOFBILL");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String RentalDate = (String) session.getAttribute("RENTALDATE");
                    String ReturnDate = (String) session.getAttribute("RETURNDATE");
                    for (Map.Entry<String, CarDTO> entry : cars.entrySet()) {
                        String key = entry.getKey();
                        CarDTO value = entry.getValue();
                        int quantity = value.getQuantity();
                        List<CarDTO> carlist = (List<CarDTO>) session.getAttribute("CARLIST");
                        for (CarDTO carDTO : carlist) {
                            if (carDTO.getId().equals(key)) {
                                if (carDTO.getQuantity() <= quantity) {
                                    request.setAttribute("CHECKOUTERROR", "Out of stocks");
                                    error = true;
                                }
                            }
                        }
                        if (error == false) {
                            if (discountCode == null) {
                                billdao.createBill(billId.toString(), username, new Date(), "Activate", totalPrice, sdf.parse(RentalDate), sdf.parse(ReturnDate));
                            } else {
                                billdao.createBill(billId.toString(), username, new Date(), "Activate", discountCode, totalPrice, sdf.parse(RentalDate), sdf.parse(ReturnDate));
                            }
                            BillDetailDAO billdetaildao = new BillDetailDAO();
                            UUID billDetailId = UUID.randomUUID();
                            int rentDate = (int) session.getAttribute("RENTDATE");
                            float totalPriceOfBillDetail = value.getQuantity() * value.getPrice() * rentDate;
                            billdetaildao.createBillDetail(billDetailId.toString(), billId.toString(), key, quantity, sdf.parse(RentalDate), sdf.parse(ReturnDate), totalPriceOfBillDetail);
                            session.removeAttribute("CARCART");
                            session.removeAttribute("CARLIST");
                            session.removeAttribute("RENTALDATE");
                            session.removeAttribute("RETURNDATE");
                            session.removeAttribute("TOTALPRICEOFBILL");
                            url = "";
                        }
                    }
                }
            }
        } catch (SQLException e) {
            log("Error at CheckoutServlet_SQL: " + e.getMessage());
        } catch (NamingException e) {
            log("Error at CheckoutServlet_Naming: " + e.getMessage());
        } catch (ParseException e) {
            log("Error at CheckoutServlet_Parse: " + e.getMessage());
        } finally {
            if (error == true) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                response.sendRedirect(url);
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
