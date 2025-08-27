package com.example.kingverse.repository;
import com.example.kingverse.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/schema_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/test_seed.sql",  executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void findAll_returnsSeedBooks() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        assertTrue(books.size() >= 2, "should load at least 2 books from seed");
        assertTrue(books.stream().anyMatch(b -> "It".equals(b.getTitle())));
        assertTrue(books.stream().anyMatch(b -> "The Dead Zone".equals(b.getTitle())));
    }

    @Test
    void save_thenFindById_roundTrips() {
        Book b = new Book(null, "Carrie", 1974, "Chamberlain", "Standalone", "Telekinetic teen", null);
        Book saved = bookRepository.save(b);
        assertNotNull(saved.getBookId(), "id should be auto-generated");

        Optional<Book> fetched = bookRepository.findById(saved.getBookId());
        assertTrue(fetched.isPresent());
        assertEquals("Carrie", fetched.get().getTitle());
    }
}
