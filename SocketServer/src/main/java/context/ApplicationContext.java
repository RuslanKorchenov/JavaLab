package context;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;

public interface ApplicationContext {
    void createComponents() throws ClassNotFoundException, IllegalAccessException, InstantiationException;

    <T> T getComponent(Class<T> componentType) throws ClassNotFoundException, IllegalAccessException;

    void createTemplate(String properties);
    JdbcTemplate getTemplate();
}
