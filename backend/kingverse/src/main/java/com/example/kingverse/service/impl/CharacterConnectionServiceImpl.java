package com.example.kingverse.service.impl;

import com.example.kingverse.model.CharacterConnection;
import com.example.kingverse.repository.CharacterConnectionRepository;
import com.example.kingverse.service.CharacterConnectionService;
import com.example.kingverse.service.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CharacterConnectionServiceImpl implements CharacterConnectionService {

    private final CharacterConnectionRepository conns;

    public CharacterConnectionServiceImpl(CharacterConnectionRepository conns) {
        this.conns = conns;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CharacterConnection> findOutgoing(Integer subjectId) {
        return conns.findBySubjectId(subjectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CharacterConnection> findIncoming(Integer objectId) {
        return conns.findByObjectId(objectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CharacterConnection> findAllInvolving(Integer characterId) {
        return conns.findAllInvolving(characterId);
    }

    @Override
    public CharacterConnection create(CharacterConnection cc) {
        cc.setConnectionId(null);
        return conns.save(cc);
    }

    @Override
    public void delete(Integer connectionId) {
        if (!conns.existsById(connectionId)) {
            throw new NotFoundException("Connection " + connectionId + " not found");
        }
        conns.deleteById(connectionId);
    }
}

