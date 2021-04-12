/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.feedback;

import huylng.utils.MyConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Shi
 */
public class FeedbackDAO implements Serializable {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public boolean createFeedback(String feedbackId, String billDetailId, String content, String rating) throws SQLException, NamingException {
        try {
            String sql = "Insert into Feedback(feedbackId, billDetailId, [content], rating) "
                    + "Values (?,?,?,?)";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, feedbackId);
                ps.setString(2, billDetailId);
                ps.setString(3, content);
                ps.setString(4, rating);
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
    private List<FeedbackDTO> feedbackList;

    public List<FeedbackDTO> getFeedbackList() {
        return this.feedbackList;
    }

    public List<FeedbackDTO> getFeedbackByBillDetailId(String id) throws SQLException, NamingException {
        try {
            String sql = "Select feedbackId, billDetailId, [content], rating "
                    + "From Feedback "
                    + "Where billDetailId = ?";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    id = rs.getString("feedbackId");
                    String billDetailId = rs.getString("billDetailId");
                    String content = rs.getString("content");
                    String rating = rs.getString("rating");
                    FeedbackDTO dto = new FeedbackDTO(id, billDetailId, content, rating);
                    if (feedbackList == null) {
                        feedbackList = new ArrayList<>();
                    }
                    feedbackList.add(dto);
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
