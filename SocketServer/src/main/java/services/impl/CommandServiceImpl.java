package services.impl;

import dao.repositories.MessageRepository;
import dao.repositories.OrderRepository;
import dao.repositories.ProductRepository;
import dao.repositories.UserRepository;
import dto.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.Message;
import models.Product;
import services.interfaces.CommandService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Setter
@Getter
public class CommandServiceImpl implements CommandService {
    private MessageRepository messageRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    @Override
    public JsonDto commandGetMessages(int size, int page) {
        List<Message> messages = messageRepository.getMessages(size, page);
        List<MessageDto> dtoMessages = new ArrayList<>();
        for (Message m: messages){
            MessageDto m1 = new MessageDto(Type.ONE,  m.getText(), m.getDate(),m.getNickname());
            dtoMessages.add(m1);
        }
        return new MessagesDto(Type.ONE, dtoMessages, "get messages");
    }

    @Override
    public ProductsDto commandGetProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> dtoProducts = new ArrayList<>();
        for (Product p: products){
            ProductDto p1 = new ProductDto(p.getName(), p.getPrice());
            dtoProducts.add(p1);
        }
        return new ProductsDto(Type.ONE, dtoProducts, "get products");
    }

    @Override
    public ActionStatusDto addProduct(String productName, int price) {
        productRepository.save(Product.builder()
                .name(productName)
                .price(price)
                .build());
        return new ActionStatusDto(Type.ONE, "product successfully added!", "status");
    }

    @Override
    public ActionStatusDto removeProduct(Long id) {
        productRepository.delete(id);
        return new ActionStatusDto(Type.ONE, "product was successfully removed!", "status");
    }

    @Override
    public JsonDto purchase(Long id, Long userId) {
        Optional<Product> optionalProduct = productRepository.find(id);
        if (optionalProduct.isPresent()) {
            orderRepository.buy(userId, id);
            return new ActionStatusDto(Type.ONE, "product successfully purchased", "purchase");
        }
        throw new IllegalStateException();
    }

    @Override
    public ActionStatusDto authorityFail() {
        return new ActionStatusDto(Type.ONE, "you don't have enough authorities(", "status");
    }
}
