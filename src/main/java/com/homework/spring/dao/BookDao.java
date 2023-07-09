package com.homework.spring.dao;

import com.homework.spring.entity.Book;

import java.util.List;

public interface BookDao {

    long add(Book book);

    void update(Book book);

    Book getById(long id);

    List<Book> getAll();
}
