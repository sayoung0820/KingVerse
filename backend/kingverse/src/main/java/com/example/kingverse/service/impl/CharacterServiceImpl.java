package com.example.kingverse.service.impl;

import com.example.kingverse.model.Book;
import com.example.kingverse.model.CharacterEntity;
import com.example.kingverse.model.ConnectionView;
import com.example.kingverse.repository.CharacterEntityRepository;
import com.example.kingverse.repository.ConnectionJdbcRepository;
import com.example.kingverse.service.CharacterService;
import com.example.kingverse.service.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CharacterServiceImpl implements CharacterService {

    private final CharacterEntityRepository chars;
    private final ConnectionJdbcRepository connections;

    public CharacterServiceImpl(CharacterEntityRepository chars, ConnectionJdbcRepository connections) {
        this.chars = chars;
        this.connections = connections;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CharacterEntity> findAll() {
        return (List<CharacterEntity>) chars.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public CharacterEntity getById(Integer id) {
        return chars.findById(id).orElseThrow(() -> new NotFoundException("Character " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CharacterEntity> searchByName(String q) {
        return chars.searchByName(q == null ? "" : q);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooks(Integer characterId) {
        // ensure character exists
        getById(characterId);
        return chars.findBooksForCharacter(characterId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConnectionView> getConnections(Integer characterId) {
        getById(characterId);
        var out = connections.findOutgoing(characterId);
        var in  = connections.findIncoming(characterId);
        out.addAll(in);
        return out;
    }


    @Override
    public CharacterEntity create(CharacterEntity c) {
        c.setCharacterId(null);
        return chars.save(c);
    }

    @Override
    public CharacterEntity update(Integer id, CharacterEntity c) {
        CharacterEntity existing = getById(id);
        existing.setName(c.getName());
        existing.setDescription(c.getDescription());
        existing.setFirstAppearance(c.getFirstAppearance());
        existing.setImageUrl(c.getImageUrl());
        return chars.save(existing);
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null) return false;
        if (!chars.existsById(id)) return false;   // provided by CrudRepository
        chars.deleteById(id);
        return true;
    }
}

