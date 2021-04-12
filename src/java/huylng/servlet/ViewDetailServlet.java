/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.servlet;

import huylng.bill.BillDAO;
import huylng.bill.BillDTO;
import huylng.billdetail.BillDetailDAO;
import huylng.billdetail.BillDetailDTO;
import huylng.car.CarDAO;
import huylng.feedback.FeedbackDAO;
import huylng.feedback.FeedbackDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "ViewDetailServlet", urlPatterns = {"/ViewDetailServlet"})
public class ViewDetailServlet extends HttpServlet {

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
        String url = "viewdetailPage";
        String billId = request.getParameter("billId");
        try {
            HttpSession session = request.getSession();
            BillDAO dao = new BillDAO();
            BillDTO dto = dao.getBillById(billId);
            BillDetailDAO billdetaildao = new BillDetailDAO();
            billdetaildao.loadBillDetailHistoryOfUser(dto.getBillId());
            List<BillDetailDTO> detailList = billdetaildao.getBillList();
            if (detailList != null) {
                Map<String, List<FeedbackDTO>> feedbackMap = new HashMap<>();
                FeedbackDAO feedbackdao = new FeedbackDAO();
                for (BillDetailDTO billDetailDTO : detailList) {
                    CarDAO cardao = new CarDAO();
                    billDetailDTO.setCardto(cardao.getCarByBillDetailId(billDetailDTO.getBillDetailId()));
                    feedbackdao.getFeedbackByBillDetailId(billDetailDTO.getBillDetailId());
                    List<FeedbackDTO> feedbackList = feedbackdao.getFeedbackList();
                    if (feedbackList != null) {
                        feedbackMap.put(billDetailDTO.getBillDetailId(), feedbackList);
                        session.setAttribute("FEEDBACKLIST", feedbackMap);
                    }
                }
                session.setAttribute("BILLDETAILLIST", detailList);
            }

        } catch (SQLException ex) {
            log("Error at ViewDetailServlet_SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            log("Error at ViewDetailServlet_Naming:" + ex.getMessage());
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
