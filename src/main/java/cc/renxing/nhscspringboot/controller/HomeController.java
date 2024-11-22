package cc.renxing.nhscspringboot.controller;

import cc.renxing.nhscspringboot.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Resource
    private ProductService productService;

    @GetMapping("/")
    public String home(Model model) {
        // 添加模型数据（如产品列表）

        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }


}
