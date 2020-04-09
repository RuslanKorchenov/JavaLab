package ru.itis.repositories.entityManager;

import org.springframework.stereotype.Component;
import ru.itis.models.Product;
import ru.itis.models.User;
import ru.itis.repositories.ProductsRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class ProductsRepositoryJpaImpl implements ProductsRepository {

    //language=HQL
    private final static String HQL_FIND_ALL = "SELECT product FROM Product product";
    //language=HQL
    private final static String HQL_FIND_BY_NAME = "SELECT product FROM Product product WHERE product.name =:name ";

    @PersistenceContext
    private EntityManager entityManagerFactory;

    @Override
    public Optional<Product> findByName(String name) {
        Query query = entityManagerFactory.createQuery(HQL_FIND_BY_NAME);
        query.setParameter("name", name);
        try {
            return Optional.of((Product) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> find(Long id) {
        return Optional.ofNullable(entityManagerFactory.find(Product.class, id));
    }

    @Override
    public List<Product> findAll() {
        return entityManagerFactory.createQuery(HQL_FIND_ALL).getResultList();
    }

    @Override
    @Transactional
    public void save(Product entity) {
        entityManagerFactory.merge(entity);
    }

    @Override
    public void delete(Long id) {
        Product product = entityManagerFactory.find(Product.class, id);
        entityManagerFactory.remove(product);
    }
}
