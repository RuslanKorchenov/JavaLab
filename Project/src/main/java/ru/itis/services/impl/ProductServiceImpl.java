package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dto.OrderDto;
import ru.itis.dto.ProductDto;
import ru.itis.models.Order;
import ru.itis.models.Product;
import ru.itis.models.User;
import ru.itis.repositories.OrderRepository;
import ru.itis.repositories.ProductsRepository;
import ru.itis.repositories.UsersRepository;
import ru.itis.services.interfaces.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void addProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .build();
        productsRepository.save(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return ProductDto.from(productsRepository.findAll());
    }

    @Override
    public void addOrder(OrderDto orderDto) {
        Optional<Product> product = productsRepository.find(orderDto.getProductId());

        if (product.isPresent()) {
            orderRepository.save(Order.builder()
                    .productId(product.get())
                    .userId(orderDto.getUserId())
                    .build());
        } else {
            throw new IllegalArgumentException();
        }
    }


}
