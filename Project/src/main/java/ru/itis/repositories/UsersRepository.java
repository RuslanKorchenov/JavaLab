package ru.itis.repositories;

import ru.itis.models.User;
import ru.itis.repositories.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<Long, User> {
    Optional<User> findByEmail(String email);
    Optional<User> findByConfirmCode(String code);
    void update(String email);
}
