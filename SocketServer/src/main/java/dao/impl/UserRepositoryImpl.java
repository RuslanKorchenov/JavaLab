package dao.impl;

import dao.repositories.UserRepository;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.AuthData;
import models.Role;
import models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@NoArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    //language=SQL
    private final String SQL_SELECT_LOGIN = "SELECT * FROM users WHERE email = ?";
    //language=SQL
    private final String SQL_SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";
    //language=SQL
    private final String SQL_SELECT_ALL = "SELECT * FROM users";
    //language=SQL
    private final String SQL_INSERT = "INSERT into users(email, password, nickname, role) values (?, ?, ?, ?)";
    //language=SQL
    private final String SQL_DELETE = "DELETE FROM users WHERE id = (?)";

    @Setter
    private JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    private RowMapper<User> userRowMapper = (row, rowNumber) -> {
        return User.builder()
                .id(row.getLong("id"))
                .authData(AuthData.builder()
                        .login(row.getString("email"))
                        .password(row.getString("password"))
                        .build())
                .nickname(row.getString("nickname"))
                .role(Role.builder()
                        .userRole(row.getString("role"))
                        .build())
                .build();
    };

    public Optional<User> login(String login, String password) {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_LOGIN, new Object[]{login, password}, userRowMapper);
            if (encoder.matches(password, user.getAuthData().getPassword()))
                return Optional.ofNullable(user);
            return Optional.empty();
    }


    @Override
    public Optional<User> find(Long id) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, userRowMapper);
    }

    @Override
    public void save(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT);
            statement.setString(1, entity.getAuthData().getLogin());
            statement.setString(2, entity.getAuthData().getPassword());
            statement.setString(3, entity.getNickname());
            statement.setString(4, entity.getRole().getUserRole());
            return statement;
        }, keyHolder);

        entity.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE, new Object[]{id});
    }
}
