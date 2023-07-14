package com.homework.spring.dao;

import com.homework.spring.entity.Author;

import java.util.List;

public interface AuthorDao {

    long add(Author author);

    com.homework.spring.dto.Author getById(long id);

    List<com.homework.spring.dto.Author> getAll();

    Long getAuthorIdByNameSurnameFatherNameAndDateOfBirth(String name, String surname, String fatherName, String dateOfBirth);
}
