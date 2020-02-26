package services.interfaces;

import context.Component;
import dao.repositories.UserRepository;
import dto.UserDto;
import server.ClientHandler;

public interface UserService extends Component {
    UserDto login(String email, String password, ClientHandler clientHandler);
    void logout(ClientHandler clientHandler);
    UserRepository getUserRepository();
}
