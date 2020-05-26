package ru.itis.repositories.entityManager;

import org.springframework.stereotype.Component;
import ru.itis.models.Chat;
import ru.itis.models.Product;
import ru.itis.repositories.ChatRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class CharRepositoryJpaImpl implements ChatRepository {
    //language=HQL
    private final static String HQL_FIND_ALL = "SELECT chat FROM Chat chat";

    //language=HQL
    private final static String HQL_FIND_BY_OWNER_ID = "SELECT chat FROM Chat  chat WHERE chat.owner.id=:id";

    @PersistenceContext
    private EntityManager entityManagerFactory;

    @Override
    public Optional<Chat> findByUserId(Long id) {
        Query query = entityManagerFactory.createQuery(HQL_FIND_BY_OWNER_ID);
        query.setParameter("id", id);
        try {
            return Optional.of((Chat) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Chat> find(Long id) {
        return Optional.ofNullable(entityManagerFactory.find(Chat.class, id));
    }

    @Override
    public List<Chat> findAll() {
        return entityManagerFactory.createQuery(HQL_FIND_ALL).getResultList();
    }

    @Override
    @Transactional
    public void save(Chat entity) {
        entityManagerFactory.merge(entity);
    }

    @Override
    public void delete(Long id) {
        Chat chat = entityManagerFactory.find(Chat.class, id);
        entityManagerFactory.remove(chat);
    }
}
