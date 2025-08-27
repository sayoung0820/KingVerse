package com.example.kingverse.service;
import com.example.kingverse.model.Book;
import com.example.kingverse.model.CharacterEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/schema_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_seed.sql",  executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CharacterServiceTest {

    @Autowired
    private CharacterService characterService;

    @Test
    void searchByName_findsPennywise() {
        List<CharacterEntity> results = characterService.searchByName("penny");
        assertFalse(results.isEmpty());
        assertTrue(results.stream().anyMatch(c -> c.getName().startsWith("Pennywise")));
    }

    @Test
    void getBooks_forCharacter_includesIt() {
        CharacterEntity penny = characterService.searchByName("Pennywise").get(0);
        List<Book> books = characterService.getBooks(penny.getCharacterId());
        assertTrue(books.stream().anyMatch(b -> "It".equals(b.getTitle())));
    }

    @Test
    void create_update_delete_character() {
        // create
        CharacterEntity c = new CharacterEntity(null, "Test Char", "Desc", null, null);
        CharacterEntity saved = characterService.create(c);
        assertNotNull(saved.getCharacterId());

        // update
        saved.setDescription("Updated");
        CharacterEntity updated = characterService.update(saved.getCharacterId(), saved);
        assertEquals("Updated", updated.getDescription());

        // delete
        characterService.delete(saved.getCharacterId());
        assertThrows(NotFoundException.class, () -> characterService.getById(saved.getCharacterId()));
    }
}

