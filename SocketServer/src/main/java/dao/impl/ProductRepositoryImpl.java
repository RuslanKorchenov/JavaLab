package dao.impl;

import dao.repositories.ProductRepository;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.Product;
import models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class ProductRepositoryImpl implements ProductRepository{
    //language=SQL
    private final String SQL_INSERT_PRODUCT = "insert into products(name, price) values (?,?);";
    //language=SQL
    private final String SQL_DELETE_PRODUCT = "DELETE from products where id = ?;";
    //language=SQL
    private final String SQL_SELECT_PRODUCTS = "SELECT * from products;";
    //language=SQL
    private final String SQL_SELECT_PRODUCT = "SELECT * from products WHERE id = ?;";

    @Setter
    private JdbcTemplate jdbcTemplate;

    public ProductRepositoryImpl(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }


    private RowMapper<Product> productRowMapper = (row , rowNumber) -> {
        return Product.builder()
                .id(row.getLong("id"))
                .name(row.getString("name"))
                .price(row.getInt("price"))
                .build();
    };

    @Override
    public Optional<Product> find(Long id) {
        try {
            Product product = jdbcTemplate.queryForObject(SQL_SELECT_PRODUCT, new Object[]{id}, productRowMapper);
            return Optional.ofNullable(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(SQL_SELECT_PRODUCTS, productRowMapper);
    }

    @Override
    public void save(Product entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT_PRODUCT);
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getPrice());
            return statement;
        }, keyHolder);
        entity.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_PRODUCT, new Object[]{id});
    }
}
