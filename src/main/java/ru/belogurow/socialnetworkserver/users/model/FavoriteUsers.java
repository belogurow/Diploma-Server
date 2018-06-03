package ru.belogurow.socialnetworkserver.users.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "favorite_users", schema = "public")
public class FavoriteUsers {

    @Id
    @NotNull
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private UUID id;

    @NonNull
    @Column(name = "from_user_id", nullable = false)
    private UUID fromUserId;

    @NonNull
    @Column(name = "to_user_id", nullable = false)
    private UUID toUserId;

    public FavoriteUsers() {
    }

    public FavoriteUsers(UUID fromUserId, UUID toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(UUID fromUserId) {
        this.fromUserId = fromUserId;
    }

    public UUID getToUserId() {
        return toUserId;
    }

    public void setToUserId(UUID toUserId) {
        this.toUserId = toUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("fromUserId", fromUserId)
                .append("toUserId", toUserId)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FavoriteUsers that = (FavoriteUsers) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(fromUserId, that.fromUserId)
                .append(toUserId, that.toUserId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(fromUserId)
                .append(toUserId)
                .toHashCode();
    }
}

