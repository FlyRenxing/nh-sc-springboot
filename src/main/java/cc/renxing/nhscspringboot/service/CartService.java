package cc.renxing.nhscspringboot.service;

import cc.renxing.nhscspringboot.model.Cart;
import cc.renxing.nhscspringboot.model.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private static final String CART_SESSION_KEY = "cart";

    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    public void addToCart(Product product, int quantity, HttpSession session) {
        Cart cart = getCart(session);
        cart.addProduct(product, quantity);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void updateQuantity(Long productId, int quantity, HttpSession session) {
        Cart cart = getCart(session);
        cart.updateProductQuantity(productId, quantity);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void removeFromCart(Long productId, HttpSession session) {
        Cart cart = getCart(session);
        cart.removeProduct(productId);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void clearCart(HttpSession session) {
        Cart cart = getCart(session);
        cart.clear();
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public double getTotalPrice(HttpSession session) {
        return getCart(session).getTotalPrice();
    }

    // 下单后清空购物车
    public void checkout(HttpSession session) {
        // 这里可以将订单保存到数据库中
        clearCart(session);
    }
}
