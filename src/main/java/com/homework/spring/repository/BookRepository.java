package com.homework.spring.repository;

import com.homework.spring.entity.Book;

import java.util.List;

public interface BookRepository {

    Long save(Book book);

    Book findById(Long id);

    List<Book> findAll();

    void delete(Book book);
}
