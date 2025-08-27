package com.example.kingverse.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("user_role")
public class UserRole {
    @Id
    @Column("user_role_id")
    private Integer userRoleId;

    @Column("user_id")
    private Integer userId;

    @Column("role_id")
    private Integer roleId;

    public UserRole() {}

    public UserRole(Integer userRoleId, Integer userId, Integer roleId) {
        this.userRoleId = userRoleId;
        this.userId = userId;
        this.roleId = roleId;
    }

    public Integer getUserRoleId() { return userRoleId; }
    public void setUserRoleId(Integer userRoleId) { this.userRoleId = userRoleId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }

    @Override
    public String toString() {
        return "UserRole{" +
                "userRoleId=" + userRoleId +
                ", userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}

