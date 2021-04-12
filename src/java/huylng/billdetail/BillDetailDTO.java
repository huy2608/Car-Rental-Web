/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.billdetail;

import huylng.car.CarDTO;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Shi
 */
public class BillDetailDTO implements Serializable{
    private String billDetailId;
    private String billId;
    private CarDTO cardto;
    private int totalCar;
    private Date rentalDate;
    private Date returnDate;
    private float totalPrice;

    public BillDetailDTO() {
    }

    public BillDetailDTO(String billDetailId, String billId, CarDTO cardto, int totalCar, Date rentalDate, Date returnDate, float totalPrice) {
        this.billDetailId = billDetailId;
        this.billId = billId;
        this.cardto = cardto;
        this.totalCar = totalCar;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.totalPrice = totalPrice;
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
     * @return the totalCar
     */
    public int getTotalCar() {
        return totalCar;
    }

    /**
     * @param totalCar the totalCar to set
     */
    public void setTotalCar(int totalCar) {
        this.totalCar = totalCar;
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
     * @return the billDetailId
     */
    public String getBillDetailId() {
        return billDetailId;
    }

    /**
     * @param billDetailId the billDetailId to set
     */
    public void setBillDetailId(String billDetailId) {
        this.billDetailId = billDetailId;
    }

    /**
     * @return the cardto
     */
    public CarDTO getCardto() {
        return cardto;
    }

    /**
     * @param cardto the cardto to set
     */
    public void setCardto(CarDTO cardto) {
        this.cardto = cardto;
    }
    
}
