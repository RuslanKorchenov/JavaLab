package ru.itis.services.interfaces;

import ru.itis.dto.SignInDto;

import java.util.Optional;

public interface SignInService {
    Optional<String> signIn(SignInDto signInDto);
}
