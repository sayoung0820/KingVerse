package com.example.kingverse.repository;

import com.example.kingverse.model.UserAccount;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {

    Optional<UserAccount> findByUsername(String username);

    @Query("""
           SELECT r.name
           FROM role r
           JOIN user_role ur ON ur.role_id = r.role_id
           WHERE ur.user_id = :userId
           """)
    List<String> findRoleNamesByUserId(@Param("userId") Integer userId);
}


