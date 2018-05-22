package ru.belogurow.socialnetworkserver.chat.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "chat_message", schema = "public")
public class ChatMessage {

    @Id
    @NotNull
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private UUID id;

    @NotNull
    @Column(name = "author_id", nullable = false)
    private UUID authorId;

    @NotNull
    @Column(name = "chat_room_id", nullable = false)
    private UUID chatRoomId;

    @NotNull
    @Column(name = "date")
    private Date date;

    @NotNull
    @Column(name = "text")
    private String text;

//    @OneToMany()
//    @JoinColumn(name = "files_id", referencedColumnName = "id")
//    private User user;
//    List<FileEntity> files;


    public ChatMessage(@NotNull UUID authorId, @NotNull UUID chatRoomId, @NotNull Date date, @NotNull String text) {
        this.authorId = authorId;
        this.chatRoomId = chatRoomId;
        this.date = date;
        this.text = text;
    }

    public ChatMessage() {
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public UUID getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(UUID chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("authorId", authorId)
                .append("chatRoomId", chatRoomId)
                .append("date", date)
                .append("text", text)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ChatMessage that = (ChatMessage) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(authorId, that.authorId)
                .append(chatRoomId, that.chatRoomId)
                .append(date, that.date)
                .append(text, that.text)
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
                .toHashCode();
    }
}
