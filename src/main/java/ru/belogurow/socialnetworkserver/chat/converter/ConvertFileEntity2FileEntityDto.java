package ru.belogurow.socialnetworkserver.chat.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.belogurow.socialnetworkserver.chat.dto.FileEntityDto;
import ru.belogurow.socialnetworkserver.chat.model.FileEntity;
import ru.belogurow.socialnetworkserver.chat.service.FileEntityService;

@Component
public class ConvertFileEntity2FileEntityDto {

    @Autowired
    private FileEntityService fileEntityService;

    public FileEntityDto convert(FileEntity fileEntity) {
        FileEntityDto fileEntityDto = new FileEntityDto();

        fileEntityDto.setId(fileEntity.getId());
        fileEntityDto.setAuthorId(fileEntity.getAuthorId());
        fileEntityDto.setTitle(fileEntity.getTitle());
        fileEntityDto.setDataUrl("/file/" + fileEntity.getId());
        fileEntityDto.setFileType(fileEntity.getFileType());
        fileEntityDto.setUpdateTime(fileEntity.getUpdateTime());

        return fileEntityDto;
    }
}
