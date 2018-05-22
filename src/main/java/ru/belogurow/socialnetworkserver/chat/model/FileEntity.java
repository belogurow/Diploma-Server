package ru.belogurow.socialnetworkserver.chat.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "file_entity", schema = "public")
public class FileEntity {

    @Id
    @NotNull
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private UUID id;

//    private UUID authorId;

    @Column(name = "title")
    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "data")
    private byte[] data;

    @NotNull
    @Column(name = "update_time")
    private Date updateTime;

    @NotNull
    @Column(name = "file_type")
    private FileType fileType;

    public FileEntity() {
    }

    public FileEntity(@Null String title, @Null byte[] data, @NotNull Date updateTime, @NotNull FileType fileType) {
        this.title = title;
        this.data = data;
        this.updateTime = updateTime;
        this.fileType = fileType;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("data", data)
                .append("updateTime", updateTime)
                .append("fileType", fileType)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FileEntity that = (FileEntity) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(title, that.title)
                .append(data, that.data)
                .append(updateTime, that.updateTime)
                .append(fileType, that.fileType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(title)
                .append(data)
                .append(updateTime)
                .append(fileType)
                .toHashCode();
    }
}
