package cc.renxing.nhscspringboot.controller;

import cc.renxing.nhscspringboot.model.Product;
import cc.renxing.nhscspringboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // 展示所有商品
    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> productEntities = productService.getAllProducts();
        model.addAttribute("products", productEntities);
        return "products"; // 指向显示所有商品的页面
    }

    // 显示特定商品的详情
    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "product-detail"; // 指向显示单个商品详情的页面
        } else {
            return "redirect:/products"; // 如果商品不存在，重定向到商品列表页
        }
    }


}
