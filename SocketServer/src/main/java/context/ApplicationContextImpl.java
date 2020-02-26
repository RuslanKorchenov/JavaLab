package context;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.reflections.Reflections;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


public class ApplicationContextImpl implements ApplicationContext {
    private Map<Class, Object> components = new HashMap<>();
    private JdbcTemplate jdbcTemplate;

    @Override
    public JdbcTemplate getTemplate() {
        return jdbcTemplate;
    }

    public void createComponents() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections();
        Set<Class<? extends Component>> classes = reflections.getSubTypesOf(Component.class);
        for (Class clazz : classes) {
            if (!clazz.isInterface()) {
                Object o = Class.forName(clazz.getPackage().getName() + "." + clazz.getSimpleName()).newInstance();
                components.putIfAbsent(clazz, o);
                System.out.println("Class: " + clazz + ", " + "Object: " + o);
            }
        }
        System.out.println("Components created!");
    }

    public <T> T getComponent(Class<T> componentType) throws ClassNotFoundException, IllegalAccessException {
        T t = null;
        for (Map.Entry<Class, Object> entry : components.entrySet()) {
            for (Class cl : entry.getKey().getInterfaces()) {
                if (cl.equals(componentType) || componentType.equals(entry.getKey())) {
                    t = (T) entry.getValue();
                    for (Field field : entry.getValue().getClass().getDeclaredFields()) {
                        Class fieldClass = Class.forName(field.getType().getPackage().getName() + "." + field.getType().getSimpleName());
                        for (Class fieldClInt : fieldClass.getInterfaces()) {
                            if (isSubComponent(fieldClInt)) {
                                field.setAccessible(true);
                                field.set(entry.getValue(), getComponent(fieldClass));
                            }
                        }
                    }
                }
            }
        }
        return t;
    }


    private boolean isSubComponent(Class clazz) {
        if (clazz.getSimpleName().equals("Component")) return true;
        while (clazz.getInterfaces().length != 0) {
            for (Class clazzz : clazz.getInterfaces()) {
                if (clazzz.getSimpleName().equals("Component")) {
                    return true;
                }
                isSubComponent(clazzz);
            }
        }
        return false;
    }


    public void createTemplate(String path) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(path));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(url);
        HikariDataSource dataSource = new HikariDataSource(config);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


}