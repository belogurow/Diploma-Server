package ru.belogurow.socialnetworkserver.chat.service;

import ru.belogurow.socialnetworkserver.chat.model.FileEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileEntityService {

    FileEntity save(FileEntity fileEntity) throws IOException;

    void update(FileEntity fileEntity);

    Optional<FileEntity> findById(UUID fileEntityId);

    List<FileEntity> findAll();

    byte[] getFileDataById(UUID fileEntityId);

    List<FileEntity> findAllByAuthorId(UUID authorId);
}
