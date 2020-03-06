package ru.itis.services.interfaces;

import ru.itis.dto.UserDto;

public interface UsersService {
    UserDto getUser(Long userId);
}
