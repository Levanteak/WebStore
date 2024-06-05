package com.example.webstore.controller;

import com.example.webstore.dto.BuyRequestDTO;
import com.example.webstore.dto.PurchaseResponseDTO;
import com.example.webstore.service.PurchaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import com.example.webstore.model.Basket;
import com.example.webstore.service.BasketService;
import com.example.webstore.dto.BasketResponseDTO;

@RestController
@RequestMapping("/baskets")
@CrossOrigin("http://localhost:3000")
@Tag(name = "Basket Controller", description = "API для управления корзинами")
public class BasketController {

    @Autowired
    private BasketService basketService;
    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/save")
    public ResponseEntity<BasketResponseDTO> addProductToBasket(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int count) {
        BasketResponseDTO basket = basketService.addProductToBasket(userId, productId, count);
        return ResponseEntity.ok(basket);
    }

    @GetMapping("/basket/{basketId}")
    public ResponseEntity<BasketResponseDTO> getBasketById(@PathVariable Long basketId) {
        BasketResponseDTO basket = basketService.getBasketById(basketId);
        return ResponseEntity.ok(basket);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BasketResponseDTO>> getBasketsByUserId(@PathVariable Long userId) {
        List<BasketResponseDTO> baskets = basketService.getBasketsByUserId(userId);
        return ResponseEntity.ok(baskets);
    }
    @PostMapping("/update/{basketId}")
    public ResponseEntity<BasketResponseDTO> updateBasket(@PathVariable Long basketId, @RequestBody Basket basketDetails) {
        BasketResponseDTO updatedBasket = basketService.updateBasket(basketId, basketDetails);
        return ResponseEntity.ok(updatedBasket);
    }

    @DeleteMapping("/delete/{basketId}")
    public ResponseEntity<Void> deleteBasket(@PathVariable Long basketId) {
        basketService.deleteBasket(basketId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public List<BasketResponseDTO> getAllBaskets() {
        return basketService.getAllBaskets();
    }

}