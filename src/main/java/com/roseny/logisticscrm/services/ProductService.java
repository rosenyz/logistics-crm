package com.roseny.logisticscrm.services;

import com.roseny.logisticscrm.dtos.requests.AddProductRequest;
import com.roseny.logisticscrm.models.Product;
import com.roseny.logisticscrm.repositories.CategoryRepository;
import com.roseny.logisticscrm.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ResponseEntity<?> addProduct(@Valid @RequestBody AddProductRequest productRequest) {
        Product product = new Product();

        product.setCategory(categoryRepository.findById(productRequest.getCategory_id()).orElse(null));
        if (product.getCategory() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такой категории не существует!");
        }

        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setDescription(productRequest.getDescription());

        productRepository.save(product);

        return ResponseEntity.ok(product);
    }

    public ResponseEntity<?> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findProductsByCategoryId(categoryId);

        if (products == null || products.isEmpty())
            return ResponseEntity.ok("Товаров пока нет.");

        return ResponseEntity.ok(products);
    }

    public ResponseEntity<?> getProduct(Long productId) {
        return ResponseEntity.ok(productRepository.findById(productId));
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public ResponseEntity<?> allProducts() {
        List<Product> products = productRepository.findAll();

        if(products.isEmpty()) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Товаров пока нет."); }

        return ResponseEntity.ok(products);
    }

    public ResponseEntity<?> deleteProduct(Long productId) throws Exception{
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new BadRequestException("Product not found"));

        productRepository.delete(product);

        return ResponseEntity.ok("Успешно удалено.");
    }
}
