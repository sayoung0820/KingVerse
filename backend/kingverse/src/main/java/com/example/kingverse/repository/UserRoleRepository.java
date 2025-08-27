package com.example.kingverse.repository;

import com.example.kingverse.model.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
    List<UserRole> findByUserId(Integer userId);
    List<UserRole> findByRoleId(Integer roleId);
}

