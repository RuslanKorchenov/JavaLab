package ru.itis.repositories;

import ru.itis.models.Chat;

import java.util.Optional;

public interface ChatRepository extends CrudRepository<Long, Chat> {
    Optional<Chat> findByUserId(Long id);
}
