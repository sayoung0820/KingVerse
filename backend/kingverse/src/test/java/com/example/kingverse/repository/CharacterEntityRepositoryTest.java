package com.example.kingverse.repository;
import com.example.kingverse.model.Book;
import com.example.kingverse.model.CharacterEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/schema_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_seed.sql",  executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CharacterEntityRepositoryTest {

    @Autowired
    CharacterEntityRepository characterRepo;

    @Test
    void searchByName_caseInsensitive() {
        List<CharacterEntity> results = characterRepo.searchByName("penny");
        assertFalse(results.isEmpty(), "expect to find Pennywise");
        assertTrue(results.stream().anyMatch(c -> c.getName().startsWith("Pennywise")));
    }

    @Test
    void findBooksForCharacter_returnsRelatedBooks() {
        List<CharacterEntity> penny = characterRepo.searchByName("Pennywise");
        assertFalse(penny.isEmpty(), "Pennywise should exist in seed");
        Integer charId = penny.get(0).getCharacterId();

        List<Book> books = characterRepo.findBooksForCharacter(charId);
        assertTrue(books.stream().anyMatch(b -> "It".equals(b.getTitle())));
    }
}

