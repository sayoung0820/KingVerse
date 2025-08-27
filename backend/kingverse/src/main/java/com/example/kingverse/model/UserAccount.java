package com.example.kingverse.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("user_account")
public class UserAccount {
    @Id
    @Column("user_id")
    private Integer userId;

    private String username;

    @Column("password_hash")
    private String passwordHash;

    private Boolean enabled;

    public UserAccount() {}

    public UserAccount(Integer userId, String username, String passwordHash, Boolean enabled) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.enabled = enabled;
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    @Override
    public String toString() {
        return "UserAccount{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}

