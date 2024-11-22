package cc.renxing.nhscspringboot.service;

import cc.renxing.nhscspringboot.model.Product;
import cc.renxing.nhscspringboot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Value("${file.upload-dir}")
    private String uploadDir;
    // 获取所有产品
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 根据ID获取产品
    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);  // 如果找不到产品，返回null
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // 删除产品
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
