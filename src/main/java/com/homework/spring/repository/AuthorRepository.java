package com.homework.spring.repository;

import com.homework.spring.entity.Author;

import java.util.List;

public interface AuthorRepository {

    Long save(Author author);

    Author findById(Long id);

    List<Author> findAll();

    Author findAuthorByNameSurnameFatherName(String name, String surname, String fatherName);

    void delete(Author author);
}
