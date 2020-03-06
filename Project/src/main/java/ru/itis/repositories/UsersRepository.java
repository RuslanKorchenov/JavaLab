package ru.itis.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import ru.itis.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<Long, User> {
    Optional<User> findByEmail(String email);
    Optional<User> findByConfirmCode(String code);
    void update(String email);
}
