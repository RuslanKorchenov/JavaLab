package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.dto.SignInDto;
import ru.itis.models.CookieValue;
import ru.itis.models.User;
import ru.itis.repositories.CookieValuesRepository;
import ru.itis.repositories.UsersRepository;
import ru.itis.services.interfaces.SignInService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class SignInServiceImpl implements SignInService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CookieValuesRepository cookieValuesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<String> signIn(SignInDto signInDto) {
        Optional<User> optionalUser = usersRepository.findByEmail(signInDto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
                String newCookie = UUID.randomUUID().toString();

                CookieValue cookieValue = CookieValue.builder()
                        .value(newCookie)
                        .user(user)
                        .build();

                cookieValuesRepository.save(cookieValue);

                return Optional.of(newCookie);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }
}
