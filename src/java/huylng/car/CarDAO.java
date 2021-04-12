/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.car;

import static huylng.servlet.StartUpServlet.NUMBEROFCARSINAPAGE;
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
public class CarDAO implements Serializable {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    private List<CarDTO> cars;

    public List<CarDTO> getCars() {
        return this.cars;
    }

    public void loadCars(int page_No)
            throws SQLException, NamingException {
        int recordIndex = NUMBEROFCARSINAPAGE * (page_No - 1);

        try {
            String sql = "Select carId, carName, color, year, category, price, quantity, createDate "
                    + "From Car Where quantity > 0 and status = 1 "
                    + "Order By createDate Desc OFFSET " + recordIndex + " Rows Fetch next " + NUMBEROFCARSINAPAGE + " Row Only";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("carId");
                    String name = rs.getString("carName");
                    String color = rs.getString("color");
                    String year = rs.getString("year");
                    String category = rs.getString("category");
                    Float price = rs.getFloat("price");
                    Date createDate = rs.getDate("createDate");
                    int quantity = rs.getInt("quantity");
                    CarDTO dto = new CarDTO(id, name, color, year, category, price, quantity, createDate);
                    if (this.cars == null) {
                        this.cars = new ArrayList<>();
                    }
                    this.cars.add(dto);
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
    }

    public int getNoOfRecords()
            throws SQLException, NamingException {
        int i = 0;
        try {
            String sql = "Select carName, color, year, category, price, quantity, createDate "
                    + "From Car "
                    + "Where status = 1";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    i++;
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
        return i;
    }

    public CarDTO getCarByIdAndSetQuantity(String id, int quantity)
            throws SQLException, NamingException {
        try {
            String sql = "Select carId, carName, color, year, category, price, quantity, createDate "
                    + "From Car "
                    + "Where carId = ? and status = 1";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    id = rs.getString("carId");
                    String name = rs.getString("carName");
                    String color = rs.getString("color");
                    String year = rs.getString("year");
                    Float price = rs.getFloat("price");
                    quantity = rs.getInt("quantity");
                    Date createDate = rs.getDate("createDate");
                    String category = rs.getString("category");
                    CarDTO dto = new CarDTO(id, name, color, year, category, price, quantity, createDate);
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

    public boolean deleteCar(String id)
            throws SQLException, NamingException {
        try {
            String sql = "Update Car "
                    + "Set status = 0 "
                    + "Where carId = ?";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, id);
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

    private List<String> categoryList;

    public List<String> getCategoryList() {
        return this.categoryList;
    }

    public String getCarCategoryById() throws SQLException, NamingException {
        try {
            String sql = "Select category "
                    + "From Car";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String category = rs.getString("category");
                    if (this.categoryList == null) {
                        this.categoryList = new ArrayList<>();
                    }
                    if (!this.categoryList.contains(category)) {
                        this.categoryList.add(category);
                    }
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

    public void searchCarByName(String name, Date rentalDate, Date returnDate, int amount, int page_No) throws SQLException, NamingException {
        int recordIndex = NUMBEROFCARSINAPAGE * (page_No - 1);
        try {
            String sql = "SELECT carId, carName, color, year, category, price, quantity, createDate "
                    + "FROM Car "
                    + "WHERE carName Like ? And quantity - ? > 0 And carId NOT IN "
                    + "("
                    + "    SELECT a.carId "
                    + "    FROM   Car a "
                    + "          Inner JOIN BillDetail b "
                    + "               ON a.carId = b.carId "
                    + "    WHERE  ((Convert(Date, rentalDate) <= Convert(Date, ?) AND Convert(Date, returnDate) >= Convert(Date, ?)) "
                    + "           OR (Convert(Date, rentalDate) < Convert(Date, ?) AND Convert(Date, returnDate) >= Convert(Date, ?)) "
                    + "           OR (Convert(Date, ?) <= Convert(Date, rentalDate) AND Convert(Date, ?) >= Convert(Date, rentalDate))) "
                    + "           And(a.quantity-b.totalCar - ? < 1) "
                    + ")"
                    + "Order By createDate Desc OFFSET " + recordIndex + " Rows Fetch next " + NUMBEROFCARSINAPAGE + " Row Only";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + name + "%");
                ps.setInt(2, amount);
                ps.setTimestamp(3, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(4, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(5, new Timestamp(returnDate.getTime()));
                ps.setTimestamp(6, new Timestamp(returnDate.getTime()));
                ps.setTimestamp(7, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(8, new Timestamp(returnDate.getTime()));
                ps.setInt(9, amount);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("carId");
                    name = rs.getString("carName");
                    String color = rs.getString("color");
                    String year = rs.getString("year");
                    Float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    Date createDate = rs.getDate("createDate");
                    String category = rs.getString("category");
                    CarDTO dto = new CarDTO(id, name, color, year, category, price, quantity, createDate);
                    if (this.cars == null) {
                        this.cars = new ArrayList<>();
                    }
                    this.cars.add(dto);
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
    }

    public void searchCarByCategory(String category, Date rentalDate, Date returnDate, int amount, int page_No) throws SQLException, NamingException {
        int recordIndex = NUMBEROFCARSINAPAGE * (page_No - 1);
        try {
            String sql = "SELECT carId, carName, color, year, category, price, quantity, createDate "
                    + "FROM Car "
                    + "WHERE category Like ? And quantity - ? > 0 And carId NOT IN "
                    + "("
                    + "    SELECT a.carId "
                    + "    FROM   Car a "
                    + "          Inner JOIN BillDetail b "
                    + "               ON a.carId = b.carId "
                    + "    WHERE  ((Convert(Date, rentalDate) <= Convert(Date, ?) AND Convert(Date, returnDate) >= Convert(Date, ?)) "
                    + "           OR (Convert(Date, rentalDate) < Convert(Date, ?) AND Convert(Date, returnDate) >= Convert(Date, ?)) "
                    + "           OR (Convert(Date, ?) <= Convert(Date, rentalDate) AND Convert(Date, ?) >= Convert(Date, rentalDate))) "
                    + "           And(a.quantity-b.totalCar-? < 1) "
                    + ")"
                    + "Order By createDate Desc OFFSET " + recordIndex + " Rows Fetch next " + NUMBEROFCARSINAPAGE + " Row Only";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + category + "%");
                ps.setInt(2, amount);
                ps.setTimestamp(3, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(4, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(5, new Timestamp(returnDate.getTime()));
                ps.setTimestamp(6, new Timestamp(returnDate.getTime()));
                ps.setTimestamp(7, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(8, new Timestamp(returnDate.getTime()));
                ps.setInt(9, amount);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("carId");
                    String name = rs.getString("carName");
                    String color = rs.getString("color");
                    String year = rs.getString("year");
                    Float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    Date createDate = rs.getDate("createDate");
                    category = rs.getString("category");
                    CarDTO dto = new CarDTO(id, name, color, year, category, price, quantity, createDate);
                    if (this.cars == null) {
                        this.cars = new ArrayList<>();
                    }
                    this.cars.add(dto);
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
    }

    public void searchCarByNameAndCategory(String name, String category, Date rentalDate, Date returnDate, int amount, int page_No) throws SQLException, NamingException {
        int recordIndex = NUMBEROFCARSINAPAGE * (page_No - 1);
        try {
            String sql = "SELECT carId, carName, color, year, category, price, quantity, createDate "
                    + "FROM Car "
                    + "WHERE carName Like ? And category Like ? And quantity - ? > 0 And carId NOT IN "
                    + "("
                    + "    SELECT a.carId "
                    + "    FROM   Car a "
                    + "          Inner JOIN BillDetail b "
                    + "               ON a.carId = b.carId "
                    + "    WHERE  ((Convert(Date, rentalDate) <= Convert(Date, ?) AND Convert(Date, returnDate) >= Convert(Date, ?)) "
                    + "           OR (Convert(Date, rentalDate) < Convert(Date, ?) AND Convert(Date, returnDate) >= Convert(Date, ?)) "
                    + "           OR (Convert(Date, ?) <= Convert(Date, rentalDate) AND Convert(Date, ?) >= Convert(Date, rentalDate))) "
                    + "           And(a.quantity-b.totalCar-? < 1) "
                    + ")"
                    + "Order By createDate Desc OFFSET " + recordIndex + " Rows Fetch next " + NUMBEROFCARSINAPAGE + " Row Only";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + name + "%");
                ps.setString(2, "%" + category + "%");
                ps.setInt(3, amount);
                ps.setTimestamp(4, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(5, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(6, new Timestamp(returnDate.getTime()));
                ps.setTimestamp(7, new Timestamp(returnDate.getTime()));
                ps.setTimestamp(8, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(9, new Timestamp(returnDate.getTime()));
                ps.setInt(10, amount);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("carId");
                    name = rs.getString("carName");
                    String color = rs.getString("color");
                    String year = rs.getString("year");
                    Float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    Date createDate = rs.getDate("createDate");
                    category = rs.getString("category");
                    CarDTO dto = new CarDTO(id, name, color, year, category, price, quantity, createDate);
                    if (this.cars == null) {
                        this.cars = new ArrayList<>();
                    }
                    this.cars.add(dto);
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
    }

    public void searchCar(Date rentalDate, Date returnDate, int amount, int page_No) throws SQLException, NamingException {
        int recordIndex = NUMBEROFCARSINAPAGE * (page_No - 1);
        try {
            String sql = "SELECT carId, carName, color, year, category, price, quantity, createDate "
                    + "FROM Car "
                    + "WHERE quantity - ? > 0 And carId NOT IN "
                    + "("
                    + "    SELECT a.carId "
                    + "    FROM   Car a "
                    + "          Inner JOIN BillDetail b "
                    + "               ON a.carId = b.carId "
                    + "    WHERE  ((Convert(Date, rentalDate) <= Convert(Date, ?) AND Convert(Date, returnDate) >= Convert(Date, ?)) "
                    + "           OR (Convert(Date, rentalDate) < Convert(Date, ?) AND Convert(Date, returnDate) >= Convert(Date, ?)) "
                    + "           OR (Convert(Date, ?) <= Convert(Date, rentalDate) AND Convert(Date, ?) >= Convert(Date, rentalDate))) "
                    + "           And(a.quantity-b.totalCar-? < 1) "
                    + ")"
                    + "Order By createDate Desc OFFSET " + recordIndex + " Rows Fetch next " + NUMBEROFCARSINAPAGE + " Row Only";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setInt(1, amount);
                ps.setTimestamp(2, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(3, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(4, new Timestamp(returnDate.getTime()));
                ps.setTimestamp(5, new Timestamp(returnDate.getTime()));
                ps.setTimestamp(6, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(7, new Timestamp(returnDate.getTime()));
                ps.setInt(8, amount);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("carId");
                    String name = rs.getString("carName");
                    String color = rs.getString("color");
                    String year = rs.getString("year");
                    Float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    Date createDate = rs.getDate("createDate");
                    String category = rs.getString("category");
                    CarDTO dto = new CarDTO(id, name, color, year, category, price, quantity, createDate);
                    if (this.cars == null) {
                        this.cars = new ArrayList<>();
                    }
                    this.cars.add(dto);
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
    }

    public int getNoOfRecord(Date rentalDate, Date returnDate, int amount) throws SQLException, NamingException {
        int i = 0;
        try {
            String sql = "SELECT carId, carName, color, year, category, price, quantity, createDate "
                    + "FROM Car "
                    + "WHERE quantity - ? > 0 And carId NOT IN "
                    + "("
                    + "    SELECT a.carId "
                    + "    FROM   Car a "
                    + "          Inner JOIN BillDetail b "
                    + "               ON a.carId = b.carId "
                    + "    WHERE  ((Convert(Date, rentalDate) <= Convert(Date, ?) AND Convert(Date, returnDate) >= Convert(Date, ?)) "
                    + "           OR (Convert(Date, rentalDate) < Convert(Date, ?) AND Convert(Date, returnDate) >= Convert(Date, ?)) "
                    + "           OR (Convert(Date, ?) <= Convert(Date, rentalDate) AND Convert(Date, ?) >= Convert(Date, rentalDate))) "
                    + "           And(a.quantity-b.totalCar-? < 1) "
                    + ")";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setInt(1, amount);
                ps.setTimestamp(2, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(3, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(4, new Timestamp(returnDate.getTime()));
                ps.setTimestamp(5, new Timestamp(returnDate.getTime()));
                ps.setTimestamp(6, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(7, new Timestamp(returnDate.getTime()));
                ps.setInt(8, amount);
                rs = ps.executeQuery();
                while (rs.next()) {
                    i++;
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
        return i;
    }

    public int getNoOfRecord(String name, Date rentalDate, Date returnDate, int amount) throws SQLException, NamingException {
        int i = 0;
        try {
            String sql = "SELECT carId, carName, color, year, category, price, quantity, createDate "
                    + "FROM Car "
                    + "WHERE carName Like ? And quantity - ? > 0 And carId NOT IN "
                    + "("
                    + "    SELECT a.carId "
                    + "    FROM   Car a "
                    + "          Inner JOIN BillDetail b "
                    + "               ON a.carId = b.carId "
                    + "    WHERE  ((Convert(Date, rentalDate) <= Convert(Date, ?) AND Convert(Date, returnDate) >= Convert(Date, ?)) "
                    + "           OR (Convert(Date, rentalDate) < Convert(Date, ?) AND Convert(Date, returnDate) >= Convert(Date, ?)) "
                    + "           OR (Convert(Date, ?) <= Convert(Date, rentalDate) AND Convert(Date, ?) >= Convert(Date, rentalDate))) "
                    + "           And(a.quantity-b.totalCar-? < 1) "
                    + ")";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + name + "%");
                ps.setInt(2, amount);
                ps.setTimestamp(3, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(4, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(5, new Timestamp(returnDate.getTime()));
                ps.setTimestamp(6, new Timestamp(returnDate.getTime()));
                ps.setTimestamp(7, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(8, new Timestamp(returnDate.getTime()));
                ps.setInt(9, amount);
                rs = ps.executeQuery();
                while (rs.next()) {
                    i++;
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
        return i;
    }

    public int getNoOfRecord(String category, Date rentalDate, Date returnDate, int amount, int page_No) throws SQLException, NamingException {
        int i = 0;
        try {
            String sql = "SELECT carId, carName, color, year, category, price, quantity, createDate "
                    + "FROM Car "
                    + "WHERE category Like ? And quantity - ? > 0 And carId NOT IN "
                    + "("
                    + "    SELECT a.carId "
                    + "    FROM   Car a "
                    + "          Inner JOIN BillDetail b "
                    + "               ON a.carId = b.carId "
                    + "    WHERE  ((Convert(Date, rentalDate) <= Convert(Date, ?) AND Convert(Date, returnDate) >= Convert(Date, ?)) "
                    + "           OR (Convert(Date, rentalDate) < Convert(Date, ?) AND Convert(Date, returnDate) >= Convert(Date, ?)) "
                    + "           OR (Convert(Date, ?) <= Convert(Date, rentalDate) AND Convert(Date, ?) >= Convert(Date, rentalDate))) "
                    + "           And(a.quantity-b.totalCar-? < 1) "
                    + ")";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + category + "%");
                ps.setInt(2, amount);
                ps.setTimestamp(3, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(4, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(5, new Timestamp(returnDate.getTime()));
                ps.setTimestamp(6, new Timestamp(returnDate.getTime()));
                ps.setTimestamp(7, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(8, new Timestamp(returnDate.getTime()));
                ps.setInt(9, amount);
                rs = ps.executeQuery();
                while (rs.next()) {
                    i++;
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
        return i;
    }

    public int getNoOfRecord(String name, String category, Date rentalDate, Date returnDate, int amount) throws SQLException, NamingException {
        int i = 0;
        try {
            String sql = "SELECT carId, carName, color, year, category, price, quantity, createDate "
                    + "FROM Car "
                    + "WHERE carName Like ? And category Like ? And quantity - ? > 0 And carId NOT IN "
                    + "("
                    + "    SELECT a.carId "
                    + "    FROM   Car a "
                    + "          Inner JOIN BillDetail b "
                    + "               ON a.carId = b.carId "
                    + "    WHERE  ((Convert(Date, rentalDate) <= Convert(Date, ?) AND Convert(Date, returnDate) >= Convert(Date, ?)) "
                    + "           OR (Convert(Date, rentalDate) < Convert(Date, ?) AND Convert(Date, returnDate) >= Convert(Date, ?)) "
                    + "           OR (Convert(Date, ?) <= Convert(Date, rentalDate) AND Convert(Date, ?) >= Convert(Date, rentalDate))) "
                    + "           And(a.quantity-b.totalCar-? < 1) "
                    + ")";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + name + "%");
                ps.setString(2, "%" + category + "%");
                ps.setInt(3, amount);
                ps.setTimestamp(4, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(5, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(6, new Timestamp(returnDate.getTime()));
                ps.setTimestamp(7, new Timestamp(returnDate.getTime()));
                ps.setTimestamp(8, new Timestamp(rentalDate.getTime()));
                ps.setTimestamp(9, new Timestamp(returnDate.getTime()));
                ps.setInt(10, amount);
                rs = ps.executeQuery();
                while (rs.next()) {
                    i++;
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
        return i;
    }

    public boolean createCar(String id, String name, String color, int year, float price, Date createDate, String category, int quantity)
            throws SQLException, NamingException {
        try {
            String sql = "Insert into Car(carId, carName, color, year, category, price, quantity, createDate, status) "
                    + "Values (?,?,?,?,?,?,?,?,?)";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, id);
                ps.setString(2, name);
                ps.setString(3, color);
                ps.setInt(4, year);
                ps.setFloat(6, price);
                ps.setTimestamp(8, new Timestamp(createDate.getTime()));
                ps.setString(5, category);
                ps.setInt(7, quantity);
                ps.setBoolean(9, true);
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

    public boolean checkExistedId(String id) throws SQLException, NamingException {
        try {
            String sql = "Select carId "
                    + "From Car "
                    + "Where carId = ?";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
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

    public CarDTO getCarByBillDetailId(String billDetailId) throws SQLException, NamingException {
        
        try {
            String sql = "Select a.carId, carName, color, year, category, price, quantity, createDate, status "
                    + "From Car a "
                    + "INNER JOIN BillDetail b "
                    + "ON a.carId = b.carId "
                    + "Where billDetailId = ?";
            con = MyConnection.getMyConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, billDetailId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String id = rs.getString("carId");
                    String name = rs.getString("carName");
                    String color = rs.getString("color");
                    String year = rs.getString("year");
                    Float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    Date createDate = rs.getDate("createDate");
                    String category = rs.getString("category");
                    CarDTO dto = new CarDTO(id, name, color, year, category, price, quantity, createDate);
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
