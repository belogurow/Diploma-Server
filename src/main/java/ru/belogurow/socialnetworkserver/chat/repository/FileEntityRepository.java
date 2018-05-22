package ru.belogurow.socialnetworkserver.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.belogurow.socialnetworkserver.chat.model.FileEntity;

import java.util.UUID;

@Repository
public interface FileEntityRepository extends JpaRepository<FileEntity, UUID> {

    @Query("SELECT data FROM FileEntity WHERE id = :fileEntityId")
    byte[] findFileDataById(@Param(value = "fileEntityId") UUID fileEntityId);
}
