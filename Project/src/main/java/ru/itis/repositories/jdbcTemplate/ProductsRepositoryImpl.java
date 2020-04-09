package ru.itis.repositories.jdbcTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.models.Product;
import ru.itis.models.Role;
import ru.itis.models.User;
import ru.itis.repositories.ProductsRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component(value = "productsRepository")
public class ProductsRepositoryImpl implements ProductsRepository {

    private static final String SQL_SELECT_BY_ID = "select * from products where id = ?";
    private static final String SQL_SELECT_BY_NAME = "select * from products where name = ?";
    private static final String SQL_SELECT_ALL = "select * from products";
    private static final String SQL_INSERT = "insert into products(name, price) values (?, ?)";
    private static final String SQL_DELETE = "DELETE FROM products WHERE id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    RowMapper<Product> productRowMapper = (row, rowNumber) -> {
        return Product.builder().id(row.getLong("id"))
                .name(row.getString("name"))
                .price(row.getLong("price"))
                .build();
    };

    @Override
    public Optional<Product> find(Long id) {
        try {
            Product product = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, productRowMapper);
            return Optional.ofNullable(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, productRowMapper);
    }

    @Override
    public void save(Product entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT);
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getPrice());
            return statement;
        }, keyHolder);

        entity.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE, new Object[]{id});
    }

    @Override
    public Optional<Product> findByName(String name) {
        try {
            Product product = jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, new Object[]{name}, productRowMapper);
            return Optional.ofNullable(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
