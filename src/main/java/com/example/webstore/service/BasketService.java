package com.example.webstore.service;

import com.example.webstore.dto.ProductDTO;
import com.example.webstore.model.Basket;
import com.example.webstore.model.Product;
import com.example.webstore.model.User;
import com.example.webstore.repository.BasketRepository;
import com.example.webstore.repository.ProductRepository;
import com.example.webstore.repository.PurchaseRepository;
import com.example.webstore.repository.UserRepository;
import com.example.webstore.dto.BasketResponseDTO;
import com.example.webstore.service.Impl.ImplBasketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class BasketService implements ImplBasketService{

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;

    public BasketResponseDTO addProductToBasket(Long userId, Long productId, int count) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Product> productOptional = productRepository.findById(productId);

        if (userOptional.isPresent() && productOptional.isPresent()) {
            User user = userOptional.get();
            Product product = productOptional.get();

            Basket basket = new Basket();
            basket.setUser(user);
            basket.setCount(count);
            basket.setTotal((double) (product.getPrice() * count));
            basket.setProducts(List.of(product));

            Basket savedBasket = basketRepository.save(basket);
            return convertToDTO(savedBasket);
        } else {
            throw new RuntimeException("User or Product not found");
        }
    }

    public BasketResponseDTO getBasketById(Long basketId) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new RuntimeException("Basket not found"));
        return convertToDTO(basket);
    }



    public List<BasketResponseDTO> getBasketsByUserId(Long userId) {
        List<Basket> baskets = basketRepository.findByUserUserId(userId);
        return baskets.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public BasketResponseDTO updateBasket(Long basketId, Basket basketDetails) {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new RuntimeException("Basket not found"));
        basket.setCount(basketDetails.getCount());
        basket.setTotal(basketDetails.getTotal());
        basket.setProducts(basketDetails.getProducts());
        basket.setBought(basketDetails.getBought());

        Basket updatedBasket = basketRepository.save(basket);
        return convertToDTO(updatedBasket);
    }

    public void deleteBasket(Long basketId) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new RuntimeException("Basket not found"));
        basket.getProducts().clear();
        basketRepository.delete(basket);
    }
    public List<BasketResponseDTO> getAllBaskets() {
        List<Basket> baskets = basketRepository.findAll();
        return baskets.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private BasketResponseDTO convertToDTO(Basket basket) {
        BasketResponseDTO dto = new BasketResponseDTO();
        dto.setBasketId(basket.getBasketId());
        dto.setUserId(basket.getUser().getUserId());
        dto.setBought(basket.getBought());
        dto.setCount(basket.getCount());
        dto.setTotal(basket.getTotal());
        dto.setProducts(basket.getProducts().stream().map(this::convertToProductDTO).collect(Collectors.toList()));
        return dto;
    }

    private ProductDTO convertToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO(product.getProductId(), product.getName(), product.getDescription(), product.getPrice(), product.getCount(), product.getCategory());
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCount(product.getCount());
        dto.setCategory(product.getCategory());
        return dto;
    }
}
