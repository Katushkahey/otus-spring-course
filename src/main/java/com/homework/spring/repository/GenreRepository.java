package com.homework.spring.repository;

import com.homework.spring.entity.Genre;

import java.util.List;

public interface GenreRepository {

    Long save(Genre genre);

    Genre findById(Long id);

    List<Genre> findAll();

    Genre findGenreByName(String name);

    void delete(Genre genre);
}
