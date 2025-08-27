package com.example.kingverse.repository;

import com.example.kingverse.model.Book;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

    // Simple search
    @Query("SELECT * FROM book WHERE LOWER(title) LIKE CONCAT('%', LOWER(:q), '%')")
    List<Book> searchByTitle(@Param("q") String q);

    @Query("SELECT * FROM book WHERE setting = :setting")
    List<Book> findBySetting(@Param("setting") String setting);

    @Query("SELECT * FROM book WHERE series = :series")
    List<Book> findBySeries(@Param("series") String series);

    // Characters that appear in a given book (returns just names if you need)
    @Query("""
           SELECT ce.* FROM character_entity ce
           JOIN character_book cb ON cb.character_id = ce.character_id
           WHERE cb.book_id = :bookId
           """)
    List<com.example.kingverse.model.CharacterEntity> findCharactersInBook(@Param("bookId") Integer bookId);
}

