/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.bill;

import huylng.discount.DiscountDTO;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Shi
 */
public class BillDTO implements Serializable {

    private String billId;
    private String username;
    private Date createDate;
    private String status;
    private DiscountDTO discountCode;
    private float totalPrice;
    private Date rentalDate;
    private Date returnDate;
    public BillDTO() {
    }

    public BillDTO(String billId, String username, Date createDate, String status, DiscountDTO discountCode, float totalPrice, Date rentalDate, Date returnDate) {
        this.billId = billId;
        this.username = username;
        this.createDate = createDate;
        this.status = status;
        this.discountCode = discountCode;
        this.totalPrice = totalPrice;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }



    /**
     * @return the billId
     */
    public String getBillId() {
        return billId;
    }

    /**
     * @param billId the billId to set
     */
    public void setBillId(String billId) {
        this.billId = billId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the discountCode
     */
    public DiscountDTO getDiscountCode() {
        return discountCode;
    }

    /**
     * @param discountCode the discountCode to set
     */
    public void setDiscountCode(DiscountDTO discountCode) {
        this.discountCode = discountCode;
    }

    /**
     * @return the totalPrice
     */
    public float getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return the rentalDate
     */
    public Date getRentalDate() {
        return rentalDate;
    }

    /**
     * @param rentalDate the rentalDate to set
     */
    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    /**
     * @return the returnDate
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * @param returnDate the returnDate to set
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

}
