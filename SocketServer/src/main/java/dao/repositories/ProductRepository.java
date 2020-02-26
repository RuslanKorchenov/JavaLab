package dao.repositories;

import context.Component;
import dao.CrudRepository;
import models.Product;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends Component, CrudRepository<Long, Product> {
    void setJdbcTemplate(JdbcTemplate template);
}
