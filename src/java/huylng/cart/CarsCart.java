/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huylng.cart;

import huylng.car.CarDTO;
import java.util.HashMap;

/**
 *
 * @author Shi
 */
public class CarsCart {

    private HashMap<String, CarDTO> carCart;

    public HashMap<String, CarDTO> getCarCart() {
        return carCart;
    }

    public CarsCart() {
    }

    public CarsCart(HashMap<String, CarDTO> carCart) {
        this.carCart = carCart;
    }

    /**
     * @param carCart the foodCart to set
     */
    public void setCarCart(HashMap<String, CarDTO> carCart) {
        this.carCart = carCart;
    }

    public void addCarToCart(CarDTO dto) {
        if (carCart == null) {
            this.carCart = new HashMap<>();
        }
        
        if (this.carCart.containsKey(dto.getId())) {
            int quantity = dto.getQuantity() + 1;
            dto.setQuantity(quantity);
        }else{
            dto.setQuantity(1);
        }
        this.carCart.put(dto.getId(), dto);
    }

    public void removeCarFromCart(CarDTO dto) {
        if (carCart == null) {
            return;
        }
        if (this.carCart.containsKey(dto.getId())) {
            this.carCart.remove(dto.getId());
        }
        if (this.carCart.isEmpty()) {
            this.carCart = null;
        }
    }
}
