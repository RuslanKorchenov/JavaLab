package ru.itis.repositories.entityManager;

import org.springframework.stereotype.Component;
import ru.itis.models.Order;
import ru.itis.repositories.OrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class OrderRepositoryJpaImpl implements OrderRepository {
    //language=HQL
    private final static String HQL_FIND_ALL = "SELECT order FROM Order order";
    @PersistenceContext
    private EntityManager entityManagerFactory;


    @Override
    public Optional<Order> find(Long id) {
        return Optional.ofNullable(entityManagerFactory.find(Order.class, id));
    }

    @Override
    public List<Order> findAll() {
        return entityManagerFactory.createQuery(HQL_FIND_ALL).getResultList();
    }

    @Override
    @Transactional
    public void save(Order entity) {
        entityManagerFactory.merge(entity);
    }

    @Override
    public void delete(Long id) {
        Order order = entityManagerFactory.find(Order.class, id);
        entityManagerFactory.remove(order);
    }
}
