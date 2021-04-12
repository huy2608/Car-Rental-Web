/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.servlet;

import huylng.billdetail.BillDetailDAO;
import huylng.car.CarDAO;
import huylng.car.CarDTO;
import static huylng.servlet.StartUpServlet.NUMBEROFCARSINAPAGE;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

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
        String name = request.getParameter("txtName");
        String rentalDate = request.getParameter("rentalDate");
        String returnDate = request.getParameter("returnDate");
        String category = request.getParameter("cbCategory");
        String amount = request.getParameter("amount");
        String page = request.getParameter("page");
        String url = "searchPage";
        int currentPage = 1;
        int noOfRecords = 1;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        boolean error = false;

        try {
            CarDAO dao = new CarDAO();
            HttpSession session = request.getSession();
            if (name == null) {
                dao.loadCars(currentPage);
                noOfRecords = dao.getNoOfRecords();
                dao.getCarCategoryById();
                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / NUMBEROFCARSINAPAGE);
                List<CarDTO> cardto = dao.getCars();
                List<String> categoryList = dao.getCategoryList();
                session.setAttribute("CATEGORYLIST", categoryList);
                session.setAttribute("CARLIST", cardto);
                session.setAttribute("CURRENTPAGE", currentPage);
                session.setAttribute("NOOFPAGE", noOfPages);
            }
            if (rentalDate.isEmpty()) {
                error = true;
                request.setAttribute("RENTALDATEERROR", "Please fill in a date");
            }
            if (returnDate.isEmpty()) {
                error = true;
                request.setAttribute("RETURNDATEERROR", "Please fill in a date");
            }
            if (amount.isEmpty()) {
                error = true;
                request.setAttribute("AMOUNTERROR", "Please fill in a number");
            }
            if (page != null) {
                currentPage = Integer.parseInt(page);
            }
            if (error == false) {
                Date rentaldate = formatter.parse(rentalDate);
                Date returndate = formatter.parse(returnDate);
                int result = returndate.compareTo(rentaldate);
                int day = rentaldate.compareTo(new Date());
                if (day < 1) {
                    request.setAttribute("INVALIDDATE", "Rent date must be after today");
                    error = true;
                } else {
                    if (result < 1) {
                        request.setAttribute("DATEERROR", "Return date must be after than rental date");
                        error = true;
                    } else {
                        if (amount != null) {
                            int Amount = Integer.parseInt(amount);
                            if (!name.isEmpty() && category.isEmpty()) {
                                dao.searchCarByName(name, rentaldate, returndate, Amount, currentPage);
                                noOfRecords = dao.getNoOfRecord(name, rentaldate, returndate, Amount);
                            }
                            if (!category.isEmpty() && name.isEmpty()) {
                                dao.searchCarByCategory(category, rentaldate, returndate, Amount, currentPage);
                                noOfRecords = dao.getNoOfRecord(category, rentaldate, returndate, Amount, 1);
                            }
                            if (!category.isEmpty() && !name.isEmpty()) {
                                dao.searchCarByNameAndCategory(name, category, rentaldate, returndate, Amount, currentPage);
                                noOfRecords = dao.getNoOfRecord(name, category, rentaldate, returndate, Amount);
                            }
                            if (category.isEmpty() && name.isEmpty()) {
                                dao.searchCar(rentaldate, returndate, Amount, currentPage);
                                noOfRecords = dao.getNoOfRecord(rentaldate, returndate, Amount);
                            }
                            List<CarDTO> carList = dao.getCars();
                            if (carList != null) {
                                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / NUMBEROFCARSINAPAGE);
                                for (CarDTO carDTO : carList) {
                                    String carId = carDTO.getId();
                                    BillDetailDAO billdao = new BillDetailDAO();
                                    int left = carDTO.getQuantity() - billdao.getTotalOfRentingCarByCarId(carId);
                                    carDTO.setQuantity(left);
                                }
                                session.setAttribute("NOOFPAGE", noOfPages);
                                session.setAttribute("CARLIST", carList);
                                session.setAttribute("CURRENTPAGE", currentPage);
                            } else {
                                request.setAttribute("SEARCHERROR", "No result");
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            log("Error at SearchServlet_SQL: " + e.getMessage());
        } catch (NamingException e) {
            log("Error at SearchServlet_Naming: " + e.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
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
