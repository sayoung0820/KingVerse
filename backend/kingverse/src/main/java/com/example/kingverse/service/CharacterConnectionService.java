package com.example.kingverse.service;

import com.example.kingverse.model.CharacterConnection;

import java.util.List;

public interface CharacterConnectionService {
    List<CharacterConnection> findOutgoing(Integer subjectId);
    List<CharacterConnection> findIncoming(Integer objectId);
    List<CharacterConnection> findAllInvolving(Integer characterId);

    CharacterConnection create(CharacterConnection cc);
    void delete(Integer connectionId);
}

