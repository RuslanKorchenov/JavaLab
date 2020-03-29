package ru.itis.services.interfaces;

import ru.itis.dto.SignInDto;
import ru.itis.dto.UserDto;

import java.util.Optional;

public interface SignInService {
    Optional<UserDto> signIn(SignInDto signInDto);
}
