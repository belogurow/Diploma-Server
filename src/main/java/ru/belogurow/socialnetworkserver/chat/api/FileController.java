package ru.belogurow.socialnetworkserver.chat.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.belogurow.socialnetworkserver.chat.converter.ConvertFileEntity2FileEntityDto;
import ru.belogurow.socialnetworkserver.chat.model.FileEntity;
import ru.belogurow.socialnetworkserver.chat.model.FileType;
import ru.belogurow.socialnetworkserver.chat.service.FileEntityService;
import ru.belogurow.socialnetworkserver.common.exception.CustomException;
import ru.belogurow.socialnetworkserver.common.exception.ErrorCode;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class FileController {

    private static Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    private FileEntityService fileEntityService;
    private ConvertFileEntity2FileEntityDto convertFileEntity2FileEntityDto;

    @RequestMapping(value = "/file/{userId}/avatar", method = RequestMethod.POST)
    public ResponseEntity uploadUserAvatar(@RequestBody MultipartFile file,
                                           @RequestPart("fileEntity") FileEntity fileEntity,
                                           @PathVariable("userId") UUID userId) throws CustomException, IOException {
        LOGGER.info("uploadUserAvatar({}, {}, {})", file.getName(), fileEntity, userId);
        //todo save to user resources

        if (file.isEmpty()) {
            throw new CustomException(ErrorCode.FILE_IS_EMPTY);
        } else {
            fileEntity.setData(file.getBytes());
            fileEntity.setFileType(FileType.IMAGE);
            fileEntity.setUpdateTime(new Date());
            return ResponseEntity.ok(convertFileEntity2FileEntityDto.convert(fileEntityService.save(fileEntity)));
        }
    }

    @RequestMapping(value = "/file/{fileId}", method = RequestMethod.GET)
    public ResponseEntity getFile(@PathVariable("fileId") UUID fileId) throws CustomException {
        LOGGER.info("getFile({})", fileId);

        Optional<FileEntity> file = fileEntityService.findById(fileId);

        if (file.isPresent()) {
            byte[] fileData = file.get().getData();

            return ResponseEntity.ok()
//                    .contentLength(gridFsFile.getLength())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(fileData);
//                    .body(new InputStreamResource(IOUtils.tostrefileData.getInputStream()));
        } else {
            throw new CustomException(ErrorCode.FILE_NOT_FOUND);
        }
    }

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public ResponseEntity getAllFiles() {
        LOGGER.info("getAllFiles()");

        return ResponseEntity.ok(fileEntityService
                .findAll()
                .parallelStream()
                .map(f -> convertFileEntity2FileEntityDto.convert(f))
                .collect(Collectors.toList()));
    }

    @Autowired
    public void setFileEntityService(FileEntityService fileEntityService) {
        this.fileEntityService = fileEntityService;
    }

    @Autowired
    public void setConvertFileEntity2FileEntityDto(ConvertFileEntity2FileEntityDto convertFileEntity2FileEntityDto) {
        this.convertFileEntity2FileEntityDto = convertFileEntity2FileEntityDto;
    }
}
