package com.example.webstore.service.Impl;

import com.example.webstore.model.Product;
import com.example.webstore.response.ProductResponse;

import java.util.List;

public interface ImplProductService {
    Product updateProduct(Long id, Product product);
    List<Product> getProductsByUserId(Long categoryId);
    Product createProduct(Product product);
    Product getProductById(Long id);
    List<ProductResponse> getAllProductResponses();
    void deleteProduct(Long id);
    List<Product> getProductsByUserIdAndCategoryId(Long userId, Long categoryId);
    void addImageToProduct(Long productId, byte[] image);
    void removeImageFromProduct(Long productId, byte[] image);
    List<byte[]> getImagesByProductId(Long productId);



}
