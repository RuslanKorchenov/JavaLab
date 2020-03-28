package ru.itis.repositories;

import ru.itis.models.FileInfo;

import java.io.File;
import java.util.Optional;

public interface FilesRepository extends CrudRepository<Long, FileInfo> {
    Optional<FileInfo> findByStorageName(String storageName);
}
