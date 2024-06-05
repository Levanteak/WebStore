package com.example.webstore.service;

import com.example.webstore.dto.PurchaseDTO;
import com.example.webstore.model.Basket;
import com.example.webstore.model.Product;
import com.example.webstore.model.Purchase;
import com.example.webstore.repository.BasketRepository;
import com.example.webstore.repository.ProductRepository;
import com.example.webstore.repository.PurchaseRepository;
import com.example.webstore.dto.PurchaseResponseDTO;
import com.example.webstore.service.Impl.ImplPurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PurchaseService implements ImplPurchaseService {

    private final BasketRepository basketRepository;
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    public PurchaseService(BasketRepository basketRepository, PurchaseRepository purchaseRepository, ProductRepository productRepository) {
        this.basketRepository = basketRepository;
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
    }

    public List<PurchaseDTO> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        return purchases.stream()
                .map(this::convertToDTO_1)
                .collect(Collectors.toList());
    }

    public List<Product> getPurchasesByUserId(Long userId) {
        return purchaseRepository.findByUserUserId(userId);
    }
    @Transactional
    @Override
    public List<PurchaseResponseDTO> buyProducts(Long userId, Long basketId) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new RuntimeException("Basket not found"));

        if (!basket.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Basket does not belong to the user");
        }

        List<PurchaseResponseDTO> purchases = new ArrayList<>();
        List<Product> updatedProducts = new ArrayList<>();

        for (Product product : basket.getProducts()) {
            if (product.getCount() < basket.getCount()) {
                throw new RuntimeException("Not enough product count for product: " + product.getName());
            }

            Purchase purchase = new Purchase();
            purchase.setUser(basket.getUser());
            purchase.setProduct(product);
            purchase.setCount(basket.getCount());
            purchase.setPurchaseDate(new Date());

            purchaseRepository.save(purchase);
            purchases.add(convertToDTO(purchase));

            product.setCount(product.getCount() - basket.getCount());
            updatedProducts.add(product);
        }

        productRepository.saveAll(updatedProducts);

        basket.getProducts().clear();
        basketRepository.save(basket);

        basketRepository.delete(basket);

        return purchases;
    }

    public List<PurchaseResponseDTO> convertToDTOList(List<Purchase> purchases) {
        return purchases.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private PurchaseDTO convertToDTO_1(Purchase purchase) {
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setPurchaseId(purchase.getPurchaseId());
        purchaseDTO.setUserId(purchase.getUser().getUserId());
        purchaseDTO.setProductId(purchase.getProduct().getProductId());
        purchaseDTO.setCount(purchase.getCount());
        purchaseDTO.setPurchaseDate(purchase.getPurchaseDate());
        return purchaseDTO;
    }

    public PurchaseResponseDTO convertToDTO(Purchase purchase) {
        PurchaseResponseDTO dto = new PurchaseResponseDTO();
        dto.setPurchaseId(purchase.getPurchaseId());
        dto.setUserId(purchase.getUser().getUserId());
        dto.setProductId(purchase.getProduct().getProductId());
        dto.setCount(purchase.getCount());
        dto.setPurchaseDate(purchase.getPurchaseDate());
        return dto;
    }
}
