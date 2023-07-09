package com.homework.spring.service;

import com.homework.spring.dao.AuthorDao;
import com.homework.spring.entity.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorDao authorDao;

    public AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public long add(Author author) {
        return authorDao.add(author);
    }

    public Author getById(long id) {
        return authorDao.getById(id);
    }

    public List<Author> getAll() {
        return authorDao.getAll();
    }
}
