package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.dto.SignUpDto;
import ru.itis.models.State;
import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;
import ru.itis.services.interfaces.EmailService;
import ru.itis.services.interfaces.SignUpService;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Component
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ExecutorService threadPool;

    @Autowired
    private EmailService emailService;

    @Override
    public void signUp(SignUpDto form) {
        User user = User.builder()
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .username(form.getUsername())
                .confirmCode(UUID.randomUUID().toString())
                .state(State.NOT_CONFIRMED)
                .build();

        threadPool.submit(() -> {
            emailService.sendMail("Регистрация", user.getConfirmCode(),
                    user.getEmail());
        });
        usersRepository.save(user);
    }

    @Override
    public boolean confirm(String code) {
        Optional<User> optionalUser = usersRepository.findByConfirmCode(code);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setState(State.CONFIRMED);
            usersRepository.update(user.getEmail());
            return true;
        }
        return false;
    }
}
