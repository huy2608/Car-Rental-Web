/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.servlet;

import huylng.car.CarDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shi
 */
@WebServlet(name = "CreateServlet", urlPatterns = {"/CreateServlet"})
public class CreateServlet extends HttpServlet {

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
        String id = request.getParameter("txtId");
        String name = request.getParameter("txtName");
        String color = request.getParameter("txtColor");
        String year = request.getParameter("txtYear");
        String category = request.getParameter("cbCategory");
        String price = request.getParameter("txtPrice");
        String quantity = request.getParameter("txtQuantity");
        boolean error = false;
        String url = "createPage";
        try {
             CarDAO dao = new CarDAO();
            if (id.length() > 50 || id.isEmpty()) {
                request.setAttribute("IDLENGTHERROR", "Length must from 1 - 50 characters");
                error = true;
            }
            if(dao.checkExistedId(id) == true){
                request.setAttribute("IDEXISTED", "CarId exist, please try a gain.");
                error = true;
            }
            if (name.length() > 50 || name.isEmpty()) {
                request.setAttribute("NAMELENGTHERROR", "Length must from 1 - 50 characters");
                error = true;
            }
            if (color.length() > 100 || color.isEmpty()) {
                request.setAttribute("COLORLENGTHERROR", "Length must from 1 - 100 characters");
                error = true;
            }
            if (year.isEmpty()) {
                request.setAttribute("YEARLENGTHERROR", "Please fill in a year");
                error = true;
            }
            if (price.isEmpty()) {
                request.setAttribute("PRICELENGTHERROR", "Please fill in a number");
                error = true;
            }
            if (quantity.isEmpty()) {
                request.setAttribute("QUANTITYLENGTHERROR", "Please fill in a number");
                error = true;
            }
            if (error != true) {
                int Year = Integer.parseInt(year);
                float Price = Float.parseFloat(price);
                int Quantity = Integer.parseInt(quantity);
                dao.createCar(id, name, color, Year, Price, new Date(), category, Quantity);
                url = "";
            }
        } catch (SQLException e) {
            log("Error at CreateServlet_SQL: " + e.getMessage());
        } catch (NamingException e) {
            log("Error at CreateServlet_Naming: " + e.getMessage());
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
