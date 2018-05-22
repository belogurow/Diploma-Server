package ru.belogurow.socialnetworkserver.chat.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;


public class ChatMessage {

    private String id;
    private String authorId;
    private Date date;
    private String text;

    public ChatMessage(String id, String authorId, Date date, String text) {
        this.id = id;
        this.authorId = authorId;
        this.date = date;
        this.text = text;
    }

    public ChatMessage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
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
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ChatMessage chatMessage = (ChatMessage) o;

        return new EqualsBuilder()
                .append(id, chatMessage.id)
                .append(authorId, chatMessage.authorId)
                .append(date, chatMessage.date)
                .append(text, chatMessage.text)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(authorId)
                .append(date)
                .append(text)
                .toHashCode();
    }
}
