package ru.belogurow.socialnetworkserver.chat.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.UUID;

public class ChatMessageDto {
    private UUID id;
    private UUID authorId;
    private UUID chatRoomId;
    private Date date;
    private String text;
    private FileEntityDto fileEntity;

    public ChatMessageDto() {
    }

    public ChatMessageDto(UUID id, UUID authorId, UUID chatRoomId, Date date, String text, FileEntityDto fileEntity) {
        this.id = id;
        this.authorId = authorId;
        this.chatRoomId = chatRoomId;
        this.date = date;
        this.text = text;
        this.fileEntity = fileEntity;
    }

    public UUID getId() {
        return id;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public UUID getChatRoomId() {
        return chatRoomId;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public void setChatRoomId(UUID chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public FileEntityDto getFileEntity() {
        return fileEntity;
    }

    public void setFileEntity(FileEntityDto fileEntity) {
        this.fileEntity = fileEntity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("authorId", authorId)
                .append("chatRoomId", chatRoomId)
                .append("date", date)
                .append("text", text)
                .append("fileEntity", fileEntity)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ChatMessageDto that = (ChatMessageDto) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(authorId, that.authorId)
                .append(chatRoomId, that.chatRoomId)
                .append(date, that.date)
                .append(text, that.text)
                .append(fileEntity, that.fileEntity)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(authorId)
                .append(chatRoomId)
                .append(date)
                .append(text)
                .append(fileEntity)
                .toHashCode();
    }
}
