package com.example.kingverse.service;

import com.example.kingverse.model.Book;
import com.example.kingverse.model.CharacterEntity;

import java.util.List;

public interface CharacterService {
    List<CharacterEntity> findAll();
    CharacterEntity getById(Integer id);
    List<CharacterEntity> searchByName(String q);
    List<Book> getBooks(Integer characterId);

    CharacterEntity create(CharacterEntity c);
    CharacterEntity update(Integer id, CharacterEntity c);
    boolean delete(Integer id);
    java.util.List<com.example.kingverse.model.ConnectionView> getConnections(Integer characterId);


}

