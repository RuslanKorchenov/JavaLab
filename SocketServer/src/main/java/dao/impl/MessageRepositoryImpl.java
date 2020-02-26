package dao.impl;

import dao.repositories.MessageRepository;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.Message;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class MessageRepositoryImpl implements MessageRepository{
    //language=SQL
    private final String SQL_INSERT_MSG = "insert into messages(user_id, text, date) values (?,?,?);";
    //language=SQL
    private final String SQL_SELECT_BY_ID = "SELECT * from messages where id = ?";
    //language=SQL
    private final String SQL_SELECT_MSG_BY_PAGE = "SELECT * FROM messages OFFSET ? LIMIT ?;";
    @Setter
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Message> messageRowMapper = (row, rowNumber) -> {
        return Message.builder()
                .id(row.getLong("id"))
                .nickname(row.getString("nickname"))
                .date(row.getTimestamp("date"))
                .text(row.getString("text"))
                .build();
    };

    public MessageRepositoryImpl(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }


    public List<Message> getMessages(int page, int size) {
        return jdbcTemplate.query(SQL_SELECT_MSG_BY_PAGE, new Object[]{page, size}, messageRowMapper);
    }

    @Override
    public Optional<Message> find(Long id) {
        try {
            Message course = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, messageRowMapper);
            return Optional.ofNullable(course);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Message> findAll() {
        return null;
    }

    @Override
    public void save(Message entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT_MSG);
            statement.setLong(1, entity.getUserId());
            statement.setString(2, entity.getText());
            statement.setTimestamp(3, new Timestamp(entity.getDate().getTime()));
            return statement;
        }, keyHolder);

        entity.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(Long aLong) {

    }
}
