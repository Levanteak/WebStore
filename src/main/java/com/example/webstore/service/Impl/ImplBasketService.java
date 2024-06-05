package com.example.webstore.service.Impl;

import com.example.webstore.model.Basket;
import com.example.webstore.dto.BasketResponseDTO;

import java.util.List;

public interface ImplBasketService {

    BasketResponseDTO addProductToBasket(Long userId, Long productId, int count);
    BasketResponseDTO getBasketById(Long basketId);
    List<BasketResponseDTO> getAllBaskets();
    List<BasketResponseDTO> getBasketsByUserId(Long userId);
    BasketResponseDTO updateBasket(Long basketId, Basket basketDetails);
    void deleteBasket(Long basketId);
}
