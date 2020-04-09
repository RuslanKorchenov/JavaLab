package ru.itis.repositories;

import ru.itis.models.FileInfo;
import ru.itis.repositories.CrudRepository;

import java.util.Optional;

public interface FilesRepository extends CrudRepository<Long, FileInfo> {
    Optional<FileInfo> findByStorageName(String storageName);
}
