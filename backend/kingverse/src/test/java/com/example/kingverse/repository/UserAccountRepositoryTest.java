package com.example.kingverse.repository;

import com.example.kingverse.model.UserAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/schema_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_seed.sql",  executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserAccountRepositoryTest {

    @Autowired
    UserAccountRepository userRepo;

    @Test
    void findByUsername_findsAdmin() {
        Optional<UserAccount> admin = userRepo.findByUsername("admin");
        assertTrue(admin.isPresent());
        assertEquals(true, admin.get().getEnabled());
    }
}

