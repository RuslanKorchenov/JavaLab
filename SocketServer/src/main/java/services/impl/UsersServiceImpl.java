package services.impl;

import dao.repositories.UserRepository;
import dto.Type;
import dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.User;
import server.ClientHandler;
import services.TokenSecurity;
import services.interfaces.UserService;

import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
public class UsersServiceImpl implements UserService {
    UserRepository userRepository;

    @Override
    public UserDto login(String email, String password, ClientHandler clientHandler) {
        Optional<User> optionalUser = userRepository.login(email, password);
        if (optionalUser.isPresent()) {
            clientHandler.getChatMultiServer().getClients().add(clientHandler);
            String token = TokenSecurity.createToken(optionalUser.get());
            return new UserDto(
                    Type.ONE,
                    optionalUser.get().getNickname(),
                    optionalUser.get().getId(),
                    token
            );
        }
        throw new IllegalStateException();
    }

    @Override
    public void logout(ClientHandler clientHandler) {
        clientHandler.getChatMultiServer().getClients().remove(clientHandler);
    }


}
