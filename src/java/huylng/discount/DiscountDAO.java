/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.discount;

import huylng.utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.naming.NamingException;

/**
 *
 * @author Shi
 */
public class DiscountDAO {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public float getSaleOffByCode(String code) throws SQLException, NamingException {
        float saleOff = 0;
        try {
            String sql = "Select discountCode, saleOff, expirationDate "
                    + "From Discount "
                    + "Where discountCode = ?";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, code);
                rs = ps.executeQuery();
                if (rs.next()) {
                    code = rs.getString("discountCode");
                    saleOff = rs.getFloat("saleOff");
                    Date expirationDate = rs.getDate("expirationDate");
                    DiscountDTO dto = new DiscountDTO(code, saleOff, expirationDate);
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
        return saleOff;
    }

    public DiscountDTO getDiscountByBillId(String billId) throws SQLException, NamingException {
        try {
            String sql = "Select a.discountCode, saleOff, expirationDate "
                    + "From Discount a "
                    + "Inner Join Bill b "
                    + "On a.discountCode = b.discountCode "
                    + "Where billId = ?";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, billId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String code = rs.getString("discountCode");
                    float saleOff = rs.getFloat("saleOff");
                    Date expirationDate = rs.getDate("expirationDate");
                    DiscountDTO dto = new DiscountDTO(code, saleOff, expirationDate);
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
        }return null;
    }
}
