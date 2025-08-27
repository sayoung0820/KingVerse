package com.example.kingverse.repository;

import com.example.kingverse.model.CharacterEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterEntityRepository extends CrudRepository<CharacterEntity, Integer> {

    @Query("SELECT * FROM character_entity WHERE LOWER(name) LIKE CONCAT('%', LOWER(:q), '%')")
    List<CharacterEntity> searchByName(@Param("q") String q);

    // Books a character appears in
    @Query("""
           SELECT b.* FROM book b
           JOIN character_book cb ON cb.book_id = b.book_id
           WHERE cb.character_id = :characterId
           """)
    List<com.example.kingverse.model.Book> findBooksForCharacter(@Param("characterId") Integer characterId);

    @org.springframework.data.jdbc.repository.query.Query("""
  SELECT
    cc.connection_id   AS connectionId,
    cc.subject_id      AS subjectId,
    cc.object_id       AS objectId,
    o.name             AS objectName,
    cc.connection_type AS connectionType,
    b.title            AS bookTitle,
    cc.note            AS note
  FROM character_connection cc
  JOIN character_entity o   ON o.character_id = cc.object_id
  LEFT JOIN book b          ON b.book_id = cc.book_context_id
  WHERE cc.subject_id = :subjectId
  ORDER BY o.name
  """)
    java.util.List<com.example.kingverse.model.ConnectionView>
    findOutgoingConnections(@org.springframework.data.repository.query.Param("subjectId") Integer subjectId);


}








