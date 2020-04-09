package ru.itis.repositories;

import ru.itis.models.Product;

import java.util.Optional;

public interface ProductsRepository extends CrudRepository<Long, Product> {
    Optional<Product> findByName(String name);
}
