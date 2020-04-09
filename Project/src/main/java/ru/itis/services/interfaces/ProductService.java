package ru.itis.services.interfaces;

import org.springframework.stereotype.Service;
import ru.itis.dto.OrderDto;
import ru.itis.dto.ProductDto;
import ru.itis.models.Product;

import java.util.List;


public interface ProductService {
    void addProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();

    void addOrder(OrderDto orderDto);
}
