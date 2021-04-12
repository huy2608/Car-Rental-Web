/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.servlet;

import huylng.car.CarDAO;
import huylng.car.CarDTO;
import huylng.user.UserDAO;
import huylng.user.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
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
@WebServlet(name = "StartUpServlet", urlPatterns = {"/StartUpServlet"})
public class StartUpServlet extends HttpServlet {

    public static final int NUMBEROFCARSINAPAGE = 10;

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
        String url = "searchPage";
        int noOfRecords = 1;
        String currentpage = request.getParameter("page");
        int currentPage = 1;
        try {
            HttpSession session = request.getSession();
            UserDAO logindao = new UserDAO();
            CarDAO cardao = new CarDAO();
            String username = (String) session.getAttribute("EMAIL");
            String password = (String) session.getAttribute("PASSWORD");
            UserDTO logindto = logindao.checkLogin(username, password);
            if (currentpage != null) {
                currentPage = Integer.parseInt(currentpage);
            }
            if (logindto != null) {
                session.setAttribute("FULLNAME", logindto.getFullname());

                noOfRecords = cardao.getNoOfRecords();
                session.setAttribute("USER", logindto);
                url = "searchPage";
            }
            cardao.loadCars(currentPage);
            noOfRecords = cardao.getNoOfRecords();
            cardao.getCarCategoryById();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / NUMBEROFCARSINAPAGE);
            List<CarDTO> cardto = cardao.getCars();
            List<String> category = cardao.getCategoryList();
            session.setAttribute("CATEGORYLIST", category);
            session.setAttribute("CARLIST", cardto);
            session.setAttribute("CURRENTPAGE", currentPage);
            session.setAttribute("NOOFPAGE", noOfPages);
        } catch (SQLException | NamingException e) {
            log("Error at StartUpServlet " + e.getMessage());
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
