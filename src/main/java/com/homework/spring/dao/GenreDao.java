package com.homework.spring.dao;

import com.homework.spring.entity.Genre;

import java.util.List;

public interface GenreDao {

    long add(Genre genre);

    Genre getById(Long id);

    List<Genre> getAll();

    Long getGenreIdByName(String name);
}
