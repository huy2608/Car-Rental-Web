/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.servlet;

import huylng.user.UserDAO;
import huylng.user.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import javax.naming.NamingException;
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
@WebServlet(name = "VerifyCodeServlet", urlPatterns = {"/VerifyCodeServlet"})
public class VerifyCodeServlet extends HttpServlet {

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
        String email = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String name = request.getParameter("name");
        String authcode = request.getParameter("authcode");
        String code = request.getParameter("code");
        String url = "verifyPage";
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("authcode");

            if (code.equals(authcode)) {
                UserDAO dao = new UserDAO();
                dao.createAccount(email, password, name, new Date());
                session.setAttribute("USERNAME", email);
                session.setAttribute("PASSWORD", password);
                session.setAttribute("FULLNAME", name);
                url = "searchPage";
            } else {
                out.println("Incorrect verification code");
            }
        }catch(SQLException e){
            log("Error at VerifyCodeServlet_SQL " + e.getMessage());
        }catch(NamingException e){
            log("Error at VerifyCodeServlet_Naming " + e.getMessage());
        }
        finally {
            response.sendRedirect(url);
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
