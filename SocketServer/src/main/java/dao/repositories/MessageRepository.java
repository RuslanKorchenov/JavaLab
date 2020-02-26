package dao.repositories;

import context.Component;
import dao.CrudRepository;
import models.Message;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.util.List;

public interface MessageRepository extends Component, CrudRepository<Long, Message> {
    List<Message> getMessages(int page, int size);
    void setJdbcTemplate(JdbcTemplate template);
}
