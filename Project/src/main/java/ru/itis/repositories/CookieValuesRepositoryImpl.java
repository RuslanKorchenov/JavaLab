package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.models.CookieValue;
import ru.itis.models.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class CookieValuesRepositoryImpl implements CookieValuesRepository {

    private static final String SQL_SELECT_VALUE = "SELECT * FROM cookie_value WHERE value = ?";
    private static final String SQL_SELECT_ALL = "select * from cookie_value";
    private static final String SQL_INSERT = "insert into cookie_value(user_id, value) values (?, ?)";
    private static final String SQL_DELETE = "DELETE FROM cookie_value WHERE value = ?";
    @Autowired
    private UsersRepository usersRepository;

    private RowMapper<CookieValue> cookieValueRowMapper = (row, rowNumber) ->
            CookieValue.builder()
                    .value(row.getString("value"))
                    .id(row.getLong("id"))
                    .user(usersRepository.find(Long.parseLong(row.getString("user_id"))).get())
                    .build();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<CookieValue> findByValue(String value) {
        return Optional.empty();
    }

    @Override
    public Optional<CookieValue> find(Long id) {
        try {
            CookieValue cookieValue = jdbcTemplate.queryForObject(SQL_SELECT_VALUE, new Object[]{id}, cookieValueRowMapper);
            return Optional.ofNullable(cookieValue);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<CookieValue> findAll() {
        return null;
    }

    @Override
    public void save(CookieValue entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT);
            statement.setString(1, entity.getUser().getId().toString());
            statement.setString(2, entity.getValue());
            return statement;
        }, keyHolder);

        entity.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(Long aLong) {

    }
}
