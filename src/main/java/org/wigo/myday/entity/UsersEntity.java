package org.wigo.myday.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name="users")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String email;
    private String name;
    private String password;
    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;

    public UsersEntity(Integer userId, String email, String name, String password, Instant createdAt) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.createdAt = createdAt;
    }

    public UsersEntity() {}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UsersEntity{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", createdDate=" + createdAt +
                '}';
    }
}
