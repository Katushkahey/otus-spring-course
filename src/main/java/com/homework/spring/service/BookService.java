package com.homework.spring.service;

import com.homework.spring.dao.AuthorDao;
import com.homework.spring.dao.BookDao;
import com.homework.spring.dao.GenreDao;
import com.homework.spring.entity.Author;
import com.homework.spring.entity.Book;
import com.homework.spring.entity.Genre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public BookService(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    public long add(Book book) {
        prepareAuthorForBookCreationOrUpdate(book.getAuthor());
        prepareGenreForBookCreationOrUpdate(book.getGenre());
        return bookDao.add(book);
    }

    public void update(Book book) {
        prepareAuthorForBookCreationOrUpdate(book.getAuthor());
        prepareGenreForBookCreationOrUpdate(book.getGenre());
        bookDao.update(book);
    }

    private void prepareAuthorForBookCreationOrUpdate(Author author) {
        if (author == null) {
            throw new IllegalArgumentException("author is not provided.");
        } else {
            if (author.getName() == null || author.getSurname() == null || author.getFatherName() == null ||
                    author.getDateOfBirth() == null) {
                throw new IllegalArgumentException("one or some of parameters (name, surname, fatherName, dateOfBirth)" +
                        "are not provided for author of book.");
            } else if (author.getId() == null) {
                Long authorId = authorDao.getAuthorIdByNameSurnameFatherNameAndDateOfBirth(author.getName(), author.getSurname(),
                        author.getFatherName(), author.getDateOfBirth());
                if (authorId == null) {
                    authorId = authorDao.add(author);
                }
                author.setId(authorId);
            }
        }
    }

    private void prepareGenreForBookCreationOrUpdate(Genre genre) {
        if (genre == null) {
            throw new IllegalArgumentException("genre is not provided.");
        } else {
            if (genre.getName() == null) {
                throw new IllegalArgumentException("genre is not set for this book.");
            } else if (genre.getId() == null) {
                Long genreId = genreDao.getGenreIdByName(genre.getName());
                if (genreId == null) {
                    genreId = genreDao.add(genre);
                }
                genre.setId(genreId);
            }
        }
    }

    public com.homework.spring.dto.Book getById(long id) {
        return bookDao.getById(id);
    }

    public List<com.homework.spring.dto.Book> getAll() {
        return bookDao.getAll();
    }
}
