package ru.belogurow.socialnetworkserver.common.familyCreators;

import org.apache.commons.io.IOUtils;
import ru.belogurow.socialnetworkserver.chat.model.FileEntity;
import ru.belogurow.socialnetworkserver.chat.model.FileType;

import java.io.IOException;
import java.util.Date;

public class FileEntityFamilyCreator {

    public static FileEntity createEmptyFileEntity(String title) {
        FileEntity emptyFileEntity = new FileEntity();

        emptyFileEntity.setTitle(title);
        emptyFileEntity.setData(null);
        emptyFileEntity.setUpdateTime(new Date());
        emptyFileEntity.setFileType(FileType.EMPTY);

        return emptyFileEntity;
    }

    public static FileEntity createJpgFileEntity(String title) throws IOException {
        FileEntity jpgFileEntity = createEmptyFileEntity(title);

        jpgFileEntity.setFileType(FileType.IMAGE);
        jpgFileEntity.setData(IOUtils.toByteArray(
                FileEntityFamilyCreator.class.getClassLoader().getResourceAsStream("chat/FileEntityData1.jpg")));

        return jpgFileEntity;
    }

    public static FileEntity createPdfFileEntity(String title) throws IOException {
        FileEntity pdfFileEntity = createEmptyFileEntity(title);

        pdfFileEntity.setFileType(FileType.PDF);
        pdfFileEntity.setData(IOUtils.toByteArray(
                FileEntityFamilyCreator.class.getClassLoader().getResourceAsStream("chat/FileEntityData3.pdf")));

        return pdfFileEntity;
    }

    public static FileEntity createStlFileEntity(String title) throws IOException {
        FileEntity stlFileEntity = createEmptyFileEntity(title);

        stlFileEntity.setFileType(FileType.STL);
        stlFileEntity.setData(IOUtils.toByteArray(
                FileEntityFamilyCreator.class.getClassLoader().getResourceAsStream("chat/FileEntityData2.stl")));

        return stlFileEntity;
    }


}
