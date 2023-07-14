package com.homework.spring.dao.impl;

import com.homework.spring.entity.Author;
import com.homework.spring.entity.Book;
import com.homework.spring.entity.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({BookDaoJdbc.class})
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    private final int INIT_BOOK_SIZE = 6;
    private final long MEDNYI_VSADNIK_ID = 1L;
    private final String MEDNYI_VSADNIK_TITLE = "Mednyi vsadnik";
    private final int MEDNYI_VSADNIK_NUMBER_OF_PAGES = 14;
    private final int MEDNYI_VSADNIK_YEAR_OF_PUBLISHING = 1833;
    private final String MEDNYI_VSADNIK_AUTHOR_SURNAME = "Pushkin";
    private final String MEDNYI_VSADNIK_GENRE = "poem";

    private final long POSLYSHAITE_ID = 2L;
    private final String POSLYSHAITE_TITLE = "Poslyshaite";
    private final int POSLYSHAITE_NUMBER_OF_PAGES = 1;
    private final int POSLYSHAITE_YEAR_OF_PUBLISHING = 1914;
    private final String POSLYSHAITE_AUTHOR_SURNAME = "Mayakovskiy";
    private final String POSLYSHAITE_GENRE = "poetry";

    private final long ANNA_KARENINA_ID = 3L;
    private final String ANNA_KARENINA_TITLE = "Anna Karenina";
    private final int ANNA_KARENINA_NUMBER_OF_PAGES = 478;
    private final int ANNA_KARENINA_YEAR_OF_PUBLISHING = 1833;
    private final String ANNA_KARENINA_AUTHOR_SURNAME = "Tolstoi";
    private final String ANNA_KARENINA_GENRE = "roman";

    private final String MEDNYI_VSADNIK_TITLE_UPDATED = "Mednyi vsadnik updated";
    private final int MEDNYI_VSADNIK_NUMBER_OF_PAGES_UPDATED = 15;
    private final int MEDNYI_VSADNIK_YEAR_OF_PUBLISHING_UPDATED = 1834;

    private static final Long POEM_ID = 1L;
    private static final String POEM_NAME = "poem";

    private static final Long PUSHKIN_ID = 1L;
    private static final String PUSHKIN_NAME = "Alexander";
    private static final String PUSHKIN_SURNAME = "Pushkin";
    private static final String PUSHKIN_FATHER_NAME = "Sergeevich";
    private static final String PUSHKIN_DATE_OF_BIRTH = ("1799-05-26");

    @Test
    void addBook() {
        Book book = new Book();
        book.setTitle("new title");
        book.setNumberOfPages(17);
        book.setYearOfPublishing(1820);

        Author author = new Author();
        author.setId(PUSHKIN_ID);
        author.setName(PUSHKIN_NAME);
        author.setSurname(PUSHKIN_SURNAME);
        author.setFatherName(PUSHKIN_FATHER_NAME);
        author.setDateOfBirth(PUSHKIN_DATE_OF_BIRTH);
        book.setAuthor(author);

        Genre genre = new Genre();
        genre.setId(POEM_ID);
        genre.setName(POEM_NAME);
        book.setGenre(genre);

        long id = bookDaoJdbc.add(book);
        assertThat(id).isEqualTo(INIT_BOOK_SIZE + 1);
        List<com.homework.spring.dto.Book> books = bookDaoJdbc.getAll();
        assertThat(books.size()).isEqualTo(INIT_BOOK_SIZE + 1);
    }

    @Test
    void updateBook() {
        com.homework.spring.dto.Book book = bookDaoJdbc.getById(MEDNYI_VSADNIK_ID);
        Book bookToUpdate = new Book(MEDNYI_VSADNIK_ID, MEDNYI_VSADNIK_TITLE_UPDATED, MEDNYI_VSADNIK_NUMBER_OF_PAGES_UPDATED,
                MEDNYI_VSADNIK_YEAR_OF_PUBLISHING_UPDATED, new Author(1L, book.getAuthorName(), book.getAuthorSurname(),
                book.getAuthorFatherName(), book.getAuthorDateOfBirth()), new Genre(2L, book.getGenre()));
        bookDaoJdbc.update(bookToUpdate);
        com.homework.spring.dto.Book updatedBook = bookDaoJdbc.getById(MEDNYI_VSADNIK_ID);
        assertThat(updatedBook.getTitle()).isEqualTo(MEDNYI_VSADNIK_TITLE_UPDATED);
        assertThat(updatedBook.getNumberOfPages()).isEqualTo(MEDNYI_VSADNIK_NUMBER_OF_PAGES_UPDATED);
        assertThat(updatedBook.getYearOfPublishing()).isEqualTo(MEDNYI_VSADNIK_YEAR_OF_PUBLISHING_UPDATED);
    }

    @Test
    void getBookById() {
        com.homework.spring.dto.Book book = bookDaoJdbc.getById(1L);
        assertThat(book.getId()).isEqualTo(MEDNYI_VSADNIK_ID);
        assertThat(book.getTitle()).isEqualTo(MEDNYI_VSADNIK_TITLE);
        assertThat(book.getNumberOfPages()).isEqualTo(MEDNYI_VSADNIK_NUMBER_OF_PAGES);
        assertThat(book.getYearOfPublishing()).isEqualTo(MEDNYI_VSADNIK_YEAR_OF_PUBLISHING);
        assertThat(book.getAuthorSurname()).isEqualTo(MEDNYI_VSADNIK_AUTHOR_SURNAME);
        assertThat(book.getGenre()).isEqualTo(MEDNYI_VSADNIK_GENRE);

    }

    @Test
    void getAllBooks() {
        List<com.homework.spring.dto.Book> books = bookDaoJdbc.getAll();
        assertThat(books.size()).isEqualTo(INIT_BOOK_SIZE);

        assertThat(books.get(0).getId()).isEqualTo(MEDNYI_VSADNIK_ID);
        assertThat(books.get(0).getTitle()).isEqualTo(MEDNYI_VSADNIK_TITLE);
        assertThat(books.get(0).getNumberOfPages()).isEqualTo(MEDNYI_VSADNIK_NUMBER_OF_PAGES);
        assertThat(books.get(0).getYearOfPublishing()).isEqualTo(MEDNYI_VSADNIK_YEAR_OF_PUBLISHING);
        assertThat(books.get(0).getAuthorSurname()).isEqualTo(MEDNYI_VSADNIK_AUTHOR_SURNAME);
        assertThat(books.get(0).getGenre()).isEqualTo(MEDNYI_VSADNIK_GENRE);

        assertThat(books.get(1).getId()).isEqualTo(POSLYSHAITE_ID);
        assertThat(books.get(1).getTitle()).isEqualTo(POSLYSHAITE_TITLE);
        assertThat(books.get(1).getNumberOfPages()).isEqualTo(POSLYSHAITE_NUMBER_OF_PAGES);
        assertThat(books.get(1).getYearOfPublishing()).isEqualTo(POSLYSHAITE_YEAR_OF_PUBLISHING);
        assertThat(books.get(1).getAuthorSurname()).isEqualTo(POSLYSHAITE_AUTHOR_SURNAME);
        assertThat(books.get(1).getGenre()).isEqualTo(POSLYSHAITE_GENRE);

        assertThat(books.get(2).getId()).isEqualTo(ANNA_KARENINA_ID);
        assertThat(books.get(2).getTitle()).isEqualTo(ANNA_KARENINA_TITLE);
        assertThat(books.get(2).getNumberOfPages()).isEqualTo(ANNA_KARENINA_NUMBER_OF_PAGES);
        assertThat(books.get(2).getYearOfPublishing()).isEqualTo(ANNA_KARENINA_YEAR_OF_PUBLISHING);
        assertThat(books.get(2).getAuthorSurname()).isEqualTo(ANNA_KARENINA_AUTHOR_SURNAME);
        assertThat(books.get(2).getGenre()).isEqualTo(ANNA_KARENINA_GENRE);
    }
}