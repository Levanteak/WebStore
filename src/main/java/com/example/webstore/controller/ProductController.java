package com.example.webstore.controller;

import com.example.webstore.exception.ProductNotFoundException;
import com.example.webstore.model.Category;
import com.example.webstore.model.Product;
import com.example.webstore.model.User;
import com.example.webstore.repository.CategoryRepository;
import com.example.webstore.repository.UserRepository;
import com.example.webstore.request.ProductRequest;
import com.example.webstore.response.ProductResponse;
import com.example.webstore.service.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@CrossOrigin("http://localhost:3000")
@Tag(name = "Product Controller", description = "API для управления продуктами")
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductController(ProductService productService, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductResponse productResponse = mapToProductResponse(product);
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProductResponses();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/get")
    public ResponseEntity<List<ProductResponse>> getProductsByUserIdAndCategoryId(@RequestParam(required = false) Long userId, @RequestParam(required = false) Long categoryId) {
        List<ProductResponse> productResponses;
        if (userId != null && categoryId != null) {
            productResponses = productService.getProductsByUserIdAndCategoryId(userId, categoryId)
                    .stream()
                    .map(this::mapToProductResponse)
                    .collect(Collectors.toList());
        } else if (userId != null) {
            productResponses = productService.getProductsByUserId(userId)
                    .stream()
                    .map(this::mapToProductResponse)
                    .collect(Collectors.toList());
        } else if (categoryId != null) {
            productResponses = productService.getProductsByCategoryId(categoryId)
                    .stream()
                    .map(this::mapToProductResponse)
                    .collect(Collectors.toList());
        } else {
            productResponses = productService.getAllProductResponses();
        }
        return ResponseEntity.ok(productResponses);
    }

    @PostMapping("/save")
    public ResponseEntity<Product> createOrUpdateProduct(@RequestBody ProductRequest productRequest) {
        Product product = new Product();
        product.setProductId(productRequest.getProductId());
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCount(productRequest.getCount());
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        User user = userRepository.findById(productRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        product.setUser(user);

        if (product.getProductId() != null) {
            Product updatedProduct = productService.updateProduct(product.getProductId(), product);
            return ResponseEntity.ok(updatedProduct);
        } else {
            Product newProduct = productService.createProduct(product);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            productService.deleteProduct(id);
            ProductResponse productResponse = mapToProductResponse(product);
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("/{productId}/images")
    public ResponseEntity<Void> uploadImage(@PathVariable("productId") Long productId,
                                            @RequestParam("image") MultipartFile image) {
        try {
            byte[] imageData = image.getBytes();
            productService.addImageToProduct(productId, imageData);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}/images")
    public ResponseEntity<Void> deleteImage(@PathVariable("id") Long productId,
                                            @RequestParam("image") MultipartFile image) {
        try {
            byte[] imageData = image.getBytes();
            productService.removeImageFromProduct(productId, imageData);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/images/{productId}")
    public ResponseEntity<List<byte[]>> getImages(@PathVariable("productId") Long productId) {
        List<byte[]> images = productService.getImagesByProductId(productId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }



    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setProductId(product.getProductId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setCount(product.getCount());
        response.setCategory(product.getCategory());
        response.setUser(mapToUserSummary(product.getUser()));
        response.setDateCreate(product.getDateCreate());
        response.setDelete(product.getDelete());
//        response.setImageData(product.getImageData());
        return response;
    }

    private ProductResponse.UserSummary mapToUserSummary(User user) {
        if (user == null) {
            return null;
        }
        ProductResponse.UserSummary userSummary = new ProductResponse.UserSummary();
        userSummary.setUserId(user.getUserId());
        userSummary.setLogin(user.getLogin());
        userSummary.setEmail(user.getEmail());
        return userSummary;
    }


}
