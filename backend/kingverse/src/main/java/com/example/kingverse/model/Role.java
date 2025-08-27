package com.example.kingverse.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("role")
public class Role {
    @Id
    @Column("role_id")
    private Integer roleId;

    private String name; // ROLE_USER, ROLE_ADMIN

    public Role() {}

    public Role(Integer roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                '}';
    }
}

