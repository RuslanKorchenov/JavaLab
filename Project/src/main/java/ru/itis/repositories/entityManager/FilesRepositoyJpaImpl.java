package ru.itis.repositories.entityManager;

import org.springframework.stereotype.Component;
import ru.itis.models.FileInfo;
import ru.itis.models.User;
import ru.itis.repositories.FilesRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class FilesRepositoyJpaImpl implements FilesRepository {
    //language=HQL
    private final static String HQL_FIND_ALL = "SELECT file FROM FileInfo file";

    //language=HQL
    private final static String HQL_FIND_BY_NAME = "SELECT file FROM FileInfo file WHERE file.storageFileName =:name";

    @PersistenceContext
    private EntityManager entityManagerFactory;

    @Override
    public Optional<FileInfo> findByStorageName(String storageName) {
        Query query = entityManagerFactory.createQuery(HQL_FIND_BY_NAME);
        query.setParameter("name", storageName);
        try {
            return Optional.of((FileInfo)query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<FileInfo> find(Long id) {
        return Optional.ofNullable(entityManagerFactory.find(FileInfo.class, id));
    }

    @Override
    public List<FileInfo> findAll() {
        return entityManagerFactory.createQuery(HQL_FIND_ALL).getResultList();
    }

    @Override
    @Transactional
    public void save(FileInfo fileInfo) {
        entityManagerFactory.merge(fileInfo);
    }

    @Override
    public void delete(Long id) {
        FileInfo fileInfo = entityManagerFactory.find(FileInfo.class, id);
        entityManagerFactory.remove(fileInfo);
    }
}
