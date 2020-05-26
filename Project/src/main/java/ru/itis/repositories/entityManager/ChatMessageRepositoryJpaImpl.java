package ru.itis.repositories.entityManager;

import org.springframework.stereotype.Component;
import ru.itis.models.Chat;
import ru.itis.models.ChatMessage;
import ru.itis.repositories.ChatMessageRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class ChatMessageRepositoryJpaImpl implements ChatMessageRepository {
    //language=HQL
    private final static String HQL_FIND_ALL = "SELECT chatMessage FROM ChatMessage chatMessage";

    //language=HQL
    private final static String HQL_FIND_BY_OWNER_ID = "SELECT chatMessage FROM ChatMessage chatMessage " +
            "join Chat chat on chat.owner.id = chatMessage.user.id where chat.owner.id =: id";

    @PersistenceContext
    private EntityManager entityManagerFactory;

    @Override
    public List<ChatMessage> getAllByChat(Chat chat) {
        Query query = entityManagerFactory.createQuery(HQL_FIND_BY_OWNER_ID);
        query.setParameter("id", chat.getOwner().getId());
        return query.getResultList();
    }

    @Override
    public Optional<ChatMessage> find(Long id) {
        return Optional.ofNullable(entityManagerFactory.find(ChatMessage.class, id));
    }

    @Override
    public List<ChatMessage> findAll() {
        return entityManagerFactory.createQuery(HQL_FIND_ALL).getResultList();
    }

    @Override
    @Transactional
    public void save(ChatMessage entity) {
        entityManagerFactory.merge(entity);
    }

    @Override
    public void delete(Long id) {
        ChatMessage chatMessage = entityManagerFactory.find(ChatMessage.class, id);
        entityManagerFactory.remove(chatMessage);
    }
}
