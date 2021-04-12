/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.servlet;

import huylng.car.CarDAO;
import huylng.car.CarDTO;
import huylng.recaptcha.VerifyUtils;
import static huylng.servlet.StartUpServlet.NUMBEROFCARSINAPAGE;
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
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        boolean valid = true;
        String url = "loginPage";
        int noOfRecords = 1;
        int currentPage = 1;
        boolean error = false;
        try {
            UserDAO userdao = new UserDAO();
            UserDTO dto = userdao.checkLogin(username, password);
            HttpSession session = request.getSession();
            CarDAO cardao = new CarDAO();
            if (dto != null) {
                if (valid) {
                    String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
                    System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
                    valid = VerifyUtils.verify(gRecaptchaResponse);
                    if (!valid) {
                        url = "loginErrPage";
                        error = true;
                    } else {
                        session.setAttribute("FULLNAME", dto.getFullname());
                        session.setAttribute(url, valid);
                        cardao.loadCars(currentPage);
                        noOfRecords = cardao.getNoOfRecords();
                        if(dto.isRole() == true){
                            session.setAttribute("ADMIN", dto.isRole());
                        }
                        session.setAttribute("USER", dto);
                        session.setAttribute("USERNAME", username);
                        session.setAttribute("PASSWORD", password);
                        url = "searchPage";
                        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / NUMBEROFCARSINAPAGE);
                        List<CarDTO> cardto = cardao.getCars();
                        session.setAttribute("CARLIST", cardto);
                        session.setAttribute("CURRENTPAGE", currentPage);
                        request.setAttribute("SEARCHVALUE", "");
                        session.setAttribute("NOOFPAGE", noOfPages);
                    }
                }
            } else {
                url = "loginErrPage";
            }
        } catch (SQLException e) {
            log("Error at LoginServlet_SQL: " + e.getMessage());
        } catch (NamingException e) {
            log("Error at LoginServlet_Naming: " + e.getMessage());
        } finally {
            if (error == true) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }else{
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
