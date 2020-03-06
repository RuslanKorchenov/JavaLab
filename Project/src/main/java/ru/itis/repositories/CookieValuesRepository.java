package ru.itis.repositories;

import ru.itis.models.CookieValue;

import java.util.Optional;

public interface CookieValuesRepository extends CrudRepository<Long, CookieValue> {
    Optional<CookieValue> findByValue(String value);
}