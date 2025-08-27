package com.example.kingverse.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("character_book")
public class CharacterBook {
    @Id
    private Integer id;

    @Column("character_id")
    private Integer characterId;

    @Column("book_id")
    private Integer bookId;

    // ENUM in DB; map as String here
    private String role; // Main, Supporting, Cameo, Mention

    public CharacterBook() {}

    public CharacterBook(Integer id, Integer characterId, Integer bookId, String role) {
        this.id = id;
        this.characterId = characterId;
        this.bookId = bookId;
        this.role = role;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getCharacterId() { return characterId; }
    public void setCharacterId(Integer characterId) { this.characterId = characterId; }

    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "CharacterBook{" +
                "id=" + id +
                ", characterId=" + characterId +
                ", bookId=" + bookId +
                ", role='" + role + '\'' +
                '}';
    }
}

