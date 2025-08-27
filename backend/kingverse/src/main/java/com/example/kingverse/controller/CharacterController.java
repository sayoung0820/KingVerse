package com.example.kingverse.controller;

import com.example.kingverse.model.Book;
import com.example.kingverse.model.CharacterEntity;
import com.example.kingverse.service.CharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {

    private final CharacterService characters;

    public CharacterController(CharacterService chars) {
        this.characters = chars;
    }


    // GET /api/characters?q=penny
    @GetMapping
    public List<CharacterEntity> all(@RequestParam(value = "q", required = false) String q) {
        if (q == null || q.isBlank()) {
            return characters.findAll();
        }
        return characters.searchByName(q);
    }

    // GET /api/characters/{id}
    @GetMapping("/{id}")
    public CharacterEntity byId(@PathVariable Integer id) {
        CharacterEntity c = characters.getById(id);
        if (c == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found");
        return c;
    }

    // GET /api/characters/{id}/books
    @GetMapping("/{id}/books")
    public List<Book> books(@PathVariable Integer id) {
        CharacterEntity c = characters.getById(id);
        if (c == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found");
        return characters.getBooks(id);
    }

    // POST /api/characters
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public CharacterEntity create(@RequestBody CharacterEntity c) {
        return characters.create(c);
    }

    // PUT /api/characters/{id}
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CharacterEntity update(@PathVariable Integer id, @RequestBody CharacterEntity c) {
        c.setCharacterId(id);
        CharacterEntity updated = characters.update(id, c);
        if (updated == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found");
        return updated;
    }

    // DELETE /api/characters/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        boolean removed = characters.delete(id);
        if (!removed) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found");
    }

    @GetMapping("/{id}/connections")
    public List<com.example.kingverse.model.ConnectionView> connections(@PathVariable Integer id) {
        return characters.getConnections(id);
    }

}
