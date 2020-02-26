package dao.repositories;

import context.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;

public interface OrderRepository extends Component {
    void buy(Long userId, Long productId);
    void setJdbcTemplate(JdbcTemplate template);
}
