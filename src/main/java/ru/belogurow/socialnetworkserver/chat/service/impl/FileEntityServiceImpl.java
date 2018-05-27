package ru.belogurow.socialnetworkserver.chat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.belogurow.socialnetworkserver.chat.model.FileEntity;
import ru.belogurow.socialnetworkserver.chat.repository.FileEntityRepository;
import ru.belogurow.socialnetworkserver.chat.service.FileEntityService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileEntityServiceImpl implements FileEntityService {

    private FileEntityRepository fileEntityRepository;

    @Override
    public FileEntity save(FileEntity fileEntity) {
        fileEntity.setUpdateTime(new Date());
        return fileEntityRepository.saveAndFlush(fileEntity);
    }

    @Override
    public void update(FileEntity fileEntity) {
        fileEntity.setUpdateTime(new Date());
        fileEntityRepository.saveAndFlush(fileEntity);
    }

    @Override
    public Optional<FileEntity> findById(UUID fileEntityId) {
        return fileEntityRepository.findById(fileEntityId);
    }

    @Override
    public List<FileEntity> findAll() {
        return fileEntityRepository.findAll();
    }

    @Override
    public List<FileEntity> findAllByAuthorId(UUID authorId) {
        return fileEntityRepository.findAllByAuthorId(authorId);
    }

    @Override
    public byte[] getFileDataById(UUID fileEntityId) {
        return fileEntityRepository.findFileDataById(fileEntityId);
    }

    @Autowired
    public void setFileEntityRepository(FileEntityRepository fileEntityRepository) {
        this.fileEntityRepository = fileEntityRepository;
    }
}
