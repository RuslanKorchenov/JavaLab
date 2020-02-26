package services.interfaces;

import context.Component;
import dao.repositories.MessageRepository;
import dao.repositories.ProductRepository;
import dao.repositories.UserRepository;
import dto.ActionStatusDto;
import dto.JsonDto;
import dto.ProductsDto;

public interface CommandService extends Component {

    JsonDto commandGetMessages(int size, int page);

    ProductsDto commandGetProducts();

    ActionStatusDto addProduct(String productName, int price);

    ActionStatusDto removeProduct(Long id);

    JsonDto purchase(Long id, Long userId);

    ActionStatusDto authorityFail();
    ProductRepository getProductRepository();
    MessageRepository getMessageRepository();
    UserRepository getUserRepository();
}
