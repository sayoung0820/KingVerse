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
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    void findAll_returnsSeedBooks() {
        List<Book> books = bookService.findAll();
        assertTrue(books.size() >= 2);
        assertTrue(books.stream().anyMatch(b -> "It".equals(b.getTitle())));
        assertTrue(books.stream().anyMatch(b -> "The Dead Zone".equals(b.getTitle())));
    }

    @Test
    void getById_throwsWhenMissing() {
        assertThrows(NotFoundException.class, () -> bookService.getById(999_999));
    }

    @Test
    void create_thenUpdate_thenDelete_roundTrips() {
        // create
        Book b = new Book(null, "Carrie", 1974, "Chamberlain", "Standalone", "Telekinetic teen", null);
        Book saved = bookService.create(b);
        assertNotNull(saved.getBookId());

        // update
        saved.setSummary("Updated summary");
        Book updated = bookService.update(saved.getBookId(), saved);
        assertEquals("Updated summary", updated.getSummary());

        // get characters for “It” just to exercise the join
        List<CharacterEntity> itChars = bookService.getCharacters(
                bookService.searchByTitle("It").get(0).getBookId()
        );
        assertTrue(itChars.stream().anyMatch(c -> c.getName().startsWith("Pennywise")));

        // delete
        bookService.delete(saved.getBookId());
        assertThrows(NotFoundException.class, () -> bookService.getById(saved.getBookId()));
    }
}
