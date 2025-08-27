package com.example.kingverse.repository;

import com.example.kingverse.model.CharacterConnection;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterConnectionRepository extends CrudRepository<CharacterConnection, Integer> {

    // All connections where this character is the subject (outgoing edges)
    List<CharacterConnection> findBySubjectId(Integer subjectId);

    // All connections where this character is the object (incoming edges)
    List<CharacterConnection> findByObjectId(Integer objectId);

    // Optional filter by type (kills, brother_of, etc.)
    List<CharacterConnection> findBySubjectIdAndConnectionType(Integer subjectId, String connectionType);

    @Query("""
           SELECT cc.* FROM character_connection cc
           WHERE (cc.subject_id = :characterId OR cc.object_id = :characterId)
           """)
    List<CharacterConnection> findAllInvolving(@Param("characterId") Integer characterId);
}
