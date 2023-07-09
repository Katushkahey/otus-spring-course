package com.homework.spring.service;

import com.homework.spring.dao.GenreDao;
import com.homework.spring.entity.Genre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreDao genreDao;

    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public long add(Genre genre) {
        return genreDao.add(genre);
    }

    public Genre getById(long id) {
        return genreDao.getById(id);
    }

    public List<Genre> getAll() {
        return genreDao.getAll();
    }
}
