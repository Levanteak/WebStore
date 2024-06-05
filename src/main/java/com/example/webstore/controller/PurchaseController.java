package com.example.webstore.controller;

import com.example.webstore.dto.BuyRequestDTO;
import com.example.webstore.dto.PurchaseDTO;
import com.example.webstore.model.Product;
import com.example.webstore.model.Purchase;
import com.example.webstore.dto.PurchaseResponseDTO;
import com.example.webstore.service.PurchaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/purchases")
@Tag(name = "Purchase Controller", description = "API для управления купленными товарами")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PurchaseDTO>> getAllPurchases() {
        List<PurchaseDTO> purchaseDTOs = purchaseService.getAllPurchases();
        return ResponseEntity.ok(purchaseDTOs);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Product>> getPurchasesByUserId(@PathVariable Long userId) {
        List<Product> purchases = purchaseService.getPurchasesByUserId(userId);
        return ResponseEntity.ok(purchases);
    }
    @PostMapping("/buy")
    public ResponseEntity<List<PurchaseResponseDTO>> buyProducts(@RequestBody BuyRequestDTO buyRequest) {
        List<PurchaseResponseDTO> purchases = purchaseService.buyProducts(buyRequest.getUserId(), buyRequest.getBasketId());
        return ResponseEntity.ok(purchases);
    }
}