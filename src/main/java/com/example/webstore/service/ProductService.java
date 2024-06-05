package com.example.webstore.service;

import com.example.webstore.exception.ProductNotFoundException;
import com.example.webstore.exception.ResourceNotFoundException;
import com.example.webstore.model.Category;
import com.example.webstore.model.Product;
import com.example.webstore.model.User;
import com.example.webstore.repository.CategoryRepository;
import com.example.webstore.repository.ImageRepository;
import com.example.webstore.repository.ProductRepository;
import com.example.webstore.repository.UserRepository;
import com.example.webstore.response.ProductResponse;
import com.example.webstore.service.Impl.ImplProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService implements ImplProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;



    @Autowired
    public ProductService(ProductRepository productRepository, UserRepository userRepository, ImageRepository imageRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProductResponses() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id, HttpStatus.NO_CONTENT));
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCount(product.getCount());
        existingProduct.setCategory(product.getCategory());

        return productRepository.save(existingProduct);
    }



    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id, HttpStatus.NO_CONTENT));

        product.setDelete(LocalDateTime.now());
        productRepository.save(product);
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId);
    }

    public List<Product> getProductsByUserId(Long userId) {
        return productRepository.findByUserUserId(userId);
    }
    public List<Product> getProductsByUserIdAndCategoryId(Long userId, Long categoryId) {
        return productRepository.findByUserUserIdAndCategoryCategoryId(userId, categoryId);
    }

    @Transactional
    public void addImageToProduct(Long productId, byte[] image) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        optionalProduct.ifPresent(product -> {
            product.addImage(image);
            productRepository.save(product);
        });
    }
    public void removeImageFromProduct(Long productId, byte[] image) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        optionalProduct.ifPresent(product -> {
            product.removeImage(image);
            productRepository.save(product);
        });
    }
    public List<byte[]> getImagesByProductId(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get().getImages();
        } else {
            throw new ResourceNotFoundException("Product not found with id " + productId);
        }
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
        return response;
    }

    private ProductResponse.UserSummary mapToUserSummary(User user) {
        ProductResponse.UserSummary userSummary = new ProductResponse.UserSummary();
        userSummary.setUserId(user.getUserId());
        userSummary.setLogin(user.getLogin());
        userSummary.setEmail(user.getEmail());
        return userSummary;
    }

}
