/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.billdetail;

import huylng.utils.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Shi
 */
public class BillDetailDAO implements Serializable {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public int getTotalOfRentingCarByCarId(String carId) throws SQLException, NamingException {
        int total = 0;
        try {
            String sql = "Select totalCar "
                    + "From BillDetail "
                    + "Where carId = ?";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, carId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    total = rs.getInt("totalCar");
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return total;
    }

    public boolean createBillDetail(String billDetailId, String billId, String carId, int totalCar, Date rentalDate, Date returnDate, float totalPrice) throws SQLException, NamingException {
        try {
            String sql = "Insert into BillDetail(billDetailId, billId, carId, totalCar, rentalDate, returnDate, totalPrice) "
                    + "Values (?,?,?,?,?,?,?)";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, billDetailId);
                ps.setString(2, billId);
                ps.setString(3, carId);
                ps.setInt(4, totalCar);
                ps.setTimestamp(5, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(6, new Timestamp(returnDate.getTime()));
                ps.setFloat(7, totalPrice);
                int result = ps.executeUpdate();
                if (result > 0) {
                    return true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    private List<BillDetailDTO> billList;

    public List<BillDetailDTO> getBillList() {
        return this.billList;
    }

    public List<BillDetailDTO> loadBillDetailHistoryOfUser(String billId) throws SQLException, NamingException {
        try {
            String sql = "Select billDetailId, billId, totalCar, rentalDate, returnDate, totalPrice "
                    + "From BillDetail "
                    + "Where billId = ?";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, billId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String billID = rs.getString("billID");
                    String billDetail = rs.getString("billDetailId");
                    int totalCar = rs.getInt("totalCar");
                    Date rentalDate = rs.getDate("rentalDate");
                    Date returnDate = rs.getDate("returnDate");
                    float totalPrice = rs.getFloat("totalPrice");
                    BillDetailDTO dto = new BillDetailDTO(billDetail, billID, null, totalCar, rentalDate, returnDate, totalPrice);
                    if (this.billList == null) {
                        this.billList = new ArrayList<>();
                    }
                    this.billList.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return this.billList;
    }
}
