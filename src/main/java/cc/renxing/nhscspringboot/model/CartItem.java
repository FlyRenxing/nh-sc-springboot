package cc.renxing.nhscspringboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {
    private Product product;
    private int quantity;


    public double getTotalPrice() {
        return this.product.getPrice() * this.quantity;
    }
}
