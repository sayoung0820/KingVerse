package com.example.kingverse.service;

import com.example.kingverse.model.Book;
import com.example.kingverse.model.CharacterEntity;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book getById(Integer id);
    List<Book> searchByTitle(String q);
    List<CharacterEntity> getCharacters(Integer bookId);

    Book create(Book book);
    Book update(Integer id, Book book);
    boolean delete(Integer id);
}
