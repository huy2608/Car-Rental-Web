/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.discount;

import java.util.Date;

/**
 *
 * @author Shi
 */
public class DiscountDTO {
    private String discountCode;
    private float saleOff;
    private Date exprirationDate;

    public DiscountDTO() {
    }

    public DiscountDTO(String discountCode, float saleOff, Date exprirationDate) {
        this.discountCode = discountCode;
        this.saleOff = saleOff;
        this.exprirationDate = exprirationDate;
    }

    /**
     * @return the discountCode
     */
    public String getDiscountCode() {
        return discountCode;
    }

    /**
     * @param discountCode the discountCode to set
     */
    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    /**
     * @return the saleOff
     */
    public float getSaleOff() {
        return saleOff;
    }

    /**
     * @param saleOff the saleOff to set
     */
    public void setSaleOff(float saleOff) {
        this.saleOff = saleOff;
    }

    /**
     * @return the exprirationDate
     */
    public Date getExprirationDate() {
        return exprirationDate;
    }

    /**
     * @param exprirationDate the exprirationDate to set
     */
    public void setExprirationDate(Date exprirationDate) {
        this.exprirationDate = exprirationDate;
    }
    
}
