/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.servlet;

import huylng.bill.BillDAO;
import huylng.bill.BillDTO;
import huylng.user.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
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
@WebServlet(name = "SearchHistoryServlet", urlPatterns = {"/SearchHistoryServlet"})
public class SearchHistoryServlet extends HttpServlet {

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
        String date = request.getParameter("calendar");
        String txtCarName = request.getParameter("txtCarName");
        String url = "historyPage";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            HttpSession session = request.getSession();
            BillDAO billdao = new BillDAO();
            UserDTO userdto = (UserDTO) session.getAttribute("USER");
            if (!date.isEmpty() && !txtCarName.isEmpty()) {
                billdao.getBillHistoryByRentalDateAndCarName(txtCarName, formatter.parse(date), txtCarName);
            } 
            if (!date.isEmpty()) {
                billdao.getBillHistoryByRentalDate(userdto.getUsername(), formatter.parse(date));
            }
            if (!txtCarName.isEmpty()) {
                billdao.getBillHistoryByCarName(userdto.getUsername(), txtCarName);
            }
            else {
                billdao.loadBillHistoryOfUser(userdto.getUsername());
            }
            List<BillDTO> list = billdao.getBillList();
            session.setAttribute("BILLLIST", list);
        } catch (SQLException e) {
            log("Error at SearchServlet_SQL: " + e.getMessage());
        } catch (NamingException e) {
            log("Error at SearchServlet_Naming: " + e.getMessage());
        } catch (ParseException e) {
            log("Error at SearchServlet_Parse: " + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
