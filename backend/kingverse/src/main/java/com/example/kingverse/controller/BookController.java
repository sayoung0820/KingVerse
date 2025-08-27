package com.example.kingverse.controller;
import com.example.kingverse.model.Book;
import com.example.kingverse.model.CharacterEntity;
import com.example.kingverse.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;



@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService books;

    public BookController(BookService books) {
        this.books = books;
    }

    // GET /api/books?q=it
    @GetMapping
    public List<Book> all(@RequestParam(value = "q", required = false) String q) {
        if (q == null || q.isBlank()) {
            return books.findAll();
        }
        // If you haven't implemented search yet, temporarily return findAll()
        return books.searchByTitle(q);
    }

    // GET /api/books/{id}
    @GetMapping("/{id}")
    public Book byId(@PathVariable Integer id) {
        Book b = books.getById(id);
        if (b == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        return b;
    }

    // GET /api/books/{id}/characters
    @GetMapping("/{id}/characters")
    public List<CharacterEntity> characters(@PathVariable Integer id) {
        // Optionally verify the book exists first
        Book b = books.getById(id);
        if (b == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        return books.getCharacters(id);
    }

    // POST /api/books
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book b) {
        // Expect b.getBookId() == null for new
        return books.create(b);
    }

    // PUT /api/books/{id}
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Book update(@PathVariable Integer id, @RequestBody Book b) {
        // Typically enforce id from path:
        b.setBookId(id);
        Book updated = books.update(id, b);
        if (updated == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        return updated;
    }

    // DELETE /api/books/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        boolean removed = books.delete(id);
        if (!removed) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
    }



}


