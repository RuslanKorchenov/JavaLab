package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.dto.SignInDto;
import ru.itis.dto.UserDto;
import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;
import ru.itis.services.interfaces.SignInService;

import java.util.Optional;
import java.util.UUID;

@Component
public class SignInServiceImpl implements SignInService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserDto> signIn(SignInDto signInDto) {
        Optional<User> optionalUser = usersRepository.findByEmail(signInDto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
                return Optional.of(UserDto.builder()
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .id(user.getId())
                        .build());
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }
}
