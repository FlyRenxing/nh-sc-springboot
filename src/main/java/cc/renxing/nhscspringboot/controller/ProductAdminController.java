package cc.renxing.nhscspringboot.controller;

import cc.renxing.nhscspringboot.model.Product;
import cc.renxing.nhscspringboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/admin/products")
@PreAuthorize("hasRole('ROLE_ADMIN')")  // 只有管理员才能访问
public class ProductAdminController {
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Autowired
    private ProductService productService;

    // 显示所有商品
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/product-list";
    }

    // 显示新增商品的表单
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product-form";
    }

    // 处理新增商品的请求
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("uploadImage") MultipartFile file) throws IOException {
        // 检查是否有文件上传
        if (!file.isEmpty()) {
            // 保存文件到指定目录
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(file.getInputStream(), filePath);

            // 设置商品的图片文件名
            product.setImage(fileName);
        }

        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    // 显示编辑商品的表单
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "admin/product-form";
        } else {
            return "redirect:/admin/products";
        }
    }

    // 处理删除商品的请求
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
