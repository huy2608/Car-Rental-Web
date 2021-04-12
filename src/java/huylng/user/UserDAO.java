/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.user;


import huylng.utils.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.naming.NamingException;

/**
 *
 * @author Shi
 */
public class UserDAO implements Serializable{
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public UserDTO checkLogin(String username, String password)
            throws SQLException, NamingException{
        UserDTO dto = null;
        try {
            con = MyConnection.getMyConnection();
            if (con != null) {
                String sql = "Select username, password, role, fullname, createDate "
                        + "From [User] "
                        + "Where username = ? and password = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                rs = ps.executeQuery();
                while (rs.next()) {
                    username = rs.getString("username");
                    password = rs.getString("password");
                    Boolean role = rs.getBoolean("role");
                    String name =  rs.getString("fullname");
                    Date createDate = rs.getDate("createDate");
                    dto = new UserDTO(username, password, role, name, createDate, "");
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
        return dto;
    }
    public boolean createAccount(String email, String password, String name, Date createDate) throws SQLException, NamingException {
        try {
            String sql = "Insert Into [User](username, password, role, fullname, createDate) "
                    + "Values (?,?,?,?,?)";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, password);
                ps.setBoolean(3, false);
                ps.setString(4, name);
                ps.setTimestamp(5, new Timestamp(createDate.getTime()));
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

}
