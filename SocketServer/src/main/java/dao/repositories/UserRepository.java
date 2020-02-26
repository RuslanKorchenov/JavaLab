package dao.repositories;

import context.Component;
import dao.CrudRepository;
import models.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.util.Optional;

public interface UserRepository extends Component, CrudRepository<Long, User> {
    Optional<User> login(String login, String password);
    void setJdbcTemplate(JdbcTemplate template);
}
