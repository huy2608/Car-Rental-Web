/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.bill;

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
public class BillDAO implements Serializable {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private List<BillDTO> billList;

    public List<BillDTO> getBillList() {
        return this.billList;
    }

    public List<BillDTO> loadBillHistoryOfUser(String username) throws SQLException, NamingException {
        try {
            String sql = "Select billID, username, createDate, status, totalPrice, rentalDate, returnDate "
                    + "From Bill "
                    + "Where username = ?";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, username);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String billID = rs.getString("billID");
                    username = rs.getString("username");
                    Date createDate = rs.getDate("createDate");
                    String status = rs.getString("status");
                    float totalPrice = rs.getFloat("totalPrice");
                    Date rentalDate = rs.getDate("rentalDate");
                    Date returnDate = rs.getDate("returnDate");
                    BillDTO dto = new BillDTO(billID, username, createDate, status, null, totalPrice, rentalDate, returnDate);
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

    public List<BillDTO> getBillHistoryByRentalDateAndCarName(String username, Date rentalDate, String name) throws SQLException, NamingException {
        try {
            String sql = "Select a.billID, a.username, a.createDate, a.status, a.totalPrice, a.rentalDate, a.returnDate "
                    + "From Bill a "
                    + "Inner Join BillDetail b "
                    + "On a.billId = b.billId "
                    + "Inner Join Car c "
                    + "On b.carId = c.carId "
                    + "Where a.username = ? and a.rentalDate = ? And c.carName like ?";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setTimestamp(2, new Timestamp(rentalDate.getTime()));
                ps.setString(3, "%" + name + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    String billID = rs.getString("billID");
                    username = rs.getString("username");
                    Date createDate = rs.getDate("createDate");
                    String status = rs.getString("status");
                    float totalPrice = rs.getFloat("totalPrice");
                    rentalDate = rs.getDate("rentalDate");
                    Date returnDate = rs.getDate("returnDate");
                    BillDTO dto = new BillDTO(billID, username, createDate, status, null, totalPrice, rentalDate, returnDate);
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

    public List<BillDTO> getBillHistoryByRentalDate(String username, Date rentalDate) throws SQLException, NamingException {
        try {
            String sql = "Select a.billID, a.username, a.createDate, a.status, a.totalPrice, a.rentalDate, a.returnDate "
                    + "From Bill a "
                    + "Inner Join BillDetail b "
                    + "On a.billId = b.billId "
                    + "Inner Join Car c "
                    + "On b.carId = c.carId "
                    + "Where a.username = ? and a.rentalDate = ?";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setTimestamp(2, new Timestamp(rentalDate.getTime()));
                rs = ps.executeQuery();
                while (rs.next()) {
                    String billID = rs.getString("billID");
                    username = rs.getString("username");
                    Date createDate = rs.getDate("createDate");
                    String status = rs.getString("status");
                    float totalPrice = rs.getFloat("totalPrice");
                    rentalDate = rs.getDate("rentalDate");
                    Date returnDate = rs.getDate("returnDate");
                    BillDTO dto = new BillDTO(billID, username, createDate, status, null, totalPrice, rentalDate, returnDate);
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

    public List<BillDTO> getBillHistoryByCarName(String username, String name) throws SQLException, NamingException {
        try {
            String sql = "Select a.billID, a.username, a.createDate, a.status, a.totalPrice, a.rentalDate, a.returnDate "
                    + "From Bill a "
                    + "Inner Join BillDetail b "
                    + "On a.billId = b.billId "
                    + "Inner Join Car c "
                    + "On b.carId = c.carId "
                    + "Where a.username = ? and  c.carName like ?";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, "%" + name + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    String billID = rs.getString("billID");
                    username = rs.getString("username");
                    Date createDate = rs.getDate("createDate");
                    String status = rs.getString("status");
                    float totalPrice = rs.getFloat("totalPrice");
                    Date rentalDate = rs.getDate("rentalDate");
                    Date returnDate = rs.getDate("returnDate");
                    BillDTO dto = new BillDTO(billID, username, createDate, status, null, totalPrice, rentalDate, returnDate);
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

    public boolean createBill(String billId, String username, Date createDate, String status, String discountCode, float totalPrice, Date rentalDate, Date returnDate) throws SQLException, NamingException {
        try {
            String sql = "Insert into Bill(billId, username, createDate, status, discountCode, totalPrice, rentalDate, returnDate ) "
                    + "Values (?,?,?,?,?,?,?,?)";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, billId);
                ps.setString(2, username);
                ps.setString(5, discountCode);
                ps.setTimestamp(3, new Timestamp(createDate.getTime()));
                ps.setFloat(6, totalPrice);
                ps.setString(4, status);
                ps.setTimestamp(7, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(8, new Timestamp(returnDate.getTime()));
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

    public boolean createBill(String billId, String username, Date createDate, String status, float totalPrice, Date rentalDate, Date returnDate) throws SQLException, NamingException {
        try {
            String sql = "Insert into Bill(billId, username, createDate, status, totalPrice, rentalDate, returnDate) "
                    + "Values (?,?,?,?,?,?,?)";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, billId);
                ps.setString(2, username);
                ps.setTimestamp(3, new Timestamp(createDate.getTime()));
                ps.setString(4, status);
                ps.setFloat(5, totalPrice);
                ps.setTimestamp(6, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(7, new Timestamp(returnDate.getTime()));
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

    public BillDTO getBillById(String billId) throws SQLException, NamingException {
        try {
            String sql = "Select billID, username, createDate, status, totalPrice, rentalDate, returnDate "
                    + "From Bill "
                    + "Where billId = ?";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, billId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String billID = rs.getString("billID");
                    String username = rs.getString("username");
                    Date createDate = rs.getDate("createDate");
                    String status = rs.getString("status");
                    float totalPrice = rs.getFloat("totalPrice");
                    Date rentalDate = rs.getDate("rentalDate");
                    Date returnDate = rs.getDate("returnDate");
                    BillDTO dto = new BillDTO(billID, username, createDate, status, null, totalPrice, rentalDate, returnDate);
                    return dto;
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
        return null;
    }
}
