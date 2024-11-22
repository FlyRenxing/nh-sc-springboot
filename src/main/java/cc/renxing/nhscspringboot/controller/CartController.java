package cc.renxing.nhscspringboot.controller;

import cc.renxing.nhscspringboot.model.Cart;
import cc.renxing.nhscspringboot.model.Product;
import cc.renxing.nhscspringboot.model.Product;
import cc.renxing.nhscspringboot.service.CartService;
import cc.renxing.nhscspringboot.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    // 显示购物车页面
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Cart cart = cartService.getCart(session);
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", cartService.getTotalPrice(session));
        return "cart";
    }

    // 处理添加商品到购物车
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam(defaultValue = "1") int quantity, HttpSession session) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            cartService.addToCart(product, quantity, session);
        }
        return "redirect:/cart";
    }

    // 修改商品数量
// 修改商品数量（fetch请求处理）
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateQuantity(@RequestBody Map<String, Object> data, HttpSession session) {
        Long productId = Long.valueOf(data.get("productId").toString());
        int quantity = Integer.parseInt(data.get("quantity").toString());

        // 更新购物车数量
        cartService.updateQuantity(productId, quantity, session);

        // 计算更新后的总价
        double totalPrice = cartService.getTotalPrice(session);

        // 返回JSON响应
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("totalPrice", totalPrice);
        return ResponseEntity.ok(response);
    }

    // 从购物车移除商品
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long productId, HttpSession session) {
        cartService.removeFromCart(productId, session);
        return "redirect:/cart";
    }

    // 清空购物车
    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        cartService.clearCart(session);
        return "redirect:/cart";
    }

    // 处理下单操作
    @PostMapping("/checkout")
    public String checkout(HttpSession session) {
        cartService.checkout(session);
        return "redirect:/cart?orderSuccess=true";  // 下单成功后重定向
    }
}
