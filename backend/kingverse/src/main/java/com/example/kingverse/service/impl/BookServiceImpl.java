package com.example.kingverse.service.impl;

import com.example.kingverse.model.Book;
import com.example.kingverse.model.CharacterEntity;
import com.example.kingverse.repository.BookRepository;
import com.example.kingverse.service.BookService;
import com.example.kingverse.service.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository books;

    public BookServiceImpl(BookRepository books) {
        this.books = books;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return (List<Book>) books.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book getById(Integer id) {
        return books.findById(id).orElseThrow(() -> new NotFoundException("Book " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> searchByTitle(String q) {
        return books.searchByTitle(q == null ? "" : q);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CharacterEntity> getCharacters(Integer bookId) {
        // also ensures book exists
        getById(bookId);
        return books.findCharactersInBook(bookId);
    }

    @Override
    public Book create(Book book) {
        book.setBookId(null);
        return books.save(book);
    }

    @Override
    public Book update(Integer id, Book book) {
        Book existing = getById(id);
        existing.setTitle(book.getTitle());
        existing.setYearPublished(book.getYearPublished());
        existing.setSetting(book.getSetting());
        existing.setSeries(book.getSeries());
        existing.setSummary(book.getSummary());
        existing.setCoverUrl(book.getCoverUrl());
        return books.save(existing);
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null) return false;
        if (!books.existsById(id)) return false;   // CrudRepository provides this
        books.deleteById(id);
        return true;
    }
}
