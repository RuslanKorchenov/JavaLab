package dao.impl;

import dao.repositories.OrderRepository;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@NoArgsConstructor
public class OrderRepositoryImpl implements OrderRepository{
    private final String SQL_INSERT_ORDER = "INSERT into orders(user_id, product_id) values(?,?);";

    @Setter
    private JdbcTemplate jdbcTemplate;

    public OrderRepositoryImpl(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    @Override
    public void buy(Long userId, Long productId) {
        jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection
                        .prepareStatement(SQL_INSERT_ORDER);
                statement.setLong(1, userId);
                statement.setLong(2, productId);
                return statement;
            });
    }
}
