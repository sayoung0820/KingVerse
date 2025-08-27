package com.example.kingverse.repository;

import com.example.kingverse.model.CharacterBook;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterBookRepository extends CrudRepository<CharacterBook, Integer> {

    List<CharacterBook> findByBookId(Integer bookId);
    List<CharacterBook> findByCharacterId(Integer characterId);

    // enforce uniqueness lookup
    @Query("""
           SELECT * FROM character_book
           WHERE character_id = :characterId AND book_id = :bookId
           """)
    Optional<CharacterBook> findByCharacterIdAndBookId(@Param("characterId") Integer characterId,
                                                       @Param("bookId") Integer bookId);
}
