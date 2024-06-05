package com.example.webstore.service.Impl;

import com.example.webstore.dto.PurchaseDTO;
import com.example.webstore.model.Product;
import com.example.webstore.model.Purchase;
import com.example.webstore.dto.PurchaseResponseDTO;

import java.util.List;

public interface ImplPurchaseService {
    List<PurchaseDTO> getAllPurchases();
    List<Product> getPurchasesByUserId(Long userId);
    List<PurchaseResponseDTO> buyProducts(Long userId, Long basketId);
}
