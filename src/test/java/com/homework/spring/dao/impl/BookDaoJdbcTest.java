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

    private final int INIT_BOOK_SIZE = 3;
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
        List<Book> books = bookDaoJdbc.getAll();
        assertThat(books.size()).isEqualTo(INIT_BOOK_SIZE + 1);
    }

    @Test
    void updateBook() {
        Book book = bookDaoJdbc.getById(1L);
        book.setTitle(MEDNYI_VSADNIK_TITLE_UPDATED);
        book.setNumberOfPages(MEDNYI_VSADNIK_NUMBER_OF_PAGES_UPDATED);
        book.setYearOfPublishing(MEDNYI_VSADNIK_YEAR_OF_PUBLISHING_UPDATED);
        bookDaoJdbc.update(book);
        Book updatedBook = bookDaoJdbc.getById(1L);
        assertThat(book.getTitle()).isEqualTo(MEDNYI_VSADNIK_TITLE_UPDATED);
        assertThat(book.getNumberOfPages()).isEqualTo(MEDNYI_VSADNIK_NUMBER_OF_PAGES_UPDATED);
        assertThat(book.getYearOfPublishing()).isEqualTo(MEDNYI_VSADNIK_YEAR_OF_PUBLISHING_UPDATED);
    }

    @Test
    void getBookById() {
        Book book = bookDaoJdbc.getById(1L);
        assertThat(book.getId()).isEqualTo(MEDNYI_VSADNIK_ID);
        assertThat(book.getTitle()).isEqualTo(MEDNYI_VSADNIK_TITLE);
        assertThat(book.getNumberOfPages()).isEqualTo(MEDNYI_VSADNIK_NUMBER_OF_PAGES);
        assertThat(book.getYearOfPublishing()).isEqualTo(MEDNYI_VSADNIK_YEAR_OF_PUBLISHING);
        assertThat(book.getAuthor().getSurname()).isEqualTo(MEDNYI_VSADNIK_AUTHOR_SURNAME);
        assertThat(book.getGenre().getName()).isEqualTo(MEDNYI_VSADNIK_GENRE);

    }

    @Test
    void getAllBooks() {
        List<Book> books = bookDaoJdbc.getAll();
        assertThat(books.size()).isEqualTo(INIT_BOOK_SIZE);

        assertThat(books.get(0).getId()).isEqualTo(MEDNYI_VSADNIK_ID);
        assertThat(books.get(0).getTitle()).isEqualTo(MEDNYI_VSADNIK_TITLE);
        assertThat(books.get(0).getNumberOfPages()).isEqualTo(MEDNYI_VSADNIK_NUMBER_OF_PAGES);
        assertThat(books.get(0).getYearOfPublishing()).isEqualTo(MEDNYI_VSADNIK_YEAR_OF_PUBLISHING);
        assertThat(books.get(0).getAuthor().getSurname()).isEqualTo(MEDNYI_VSADNIK_AUTHOR_SURNAME);
        assertThat(books.get(0).getGenre().getName()).isEqualTo(MEDNYI_VSADNIK_GENRE);

        assertThat(books.get(1).getId()).isEqualTo(POSLYSHAITE_ID);
        assertThat(books.get(1).getTitle()).isEqualTo(POSLYSHAITE_TITLE);
        assertThat(books.get(1).getNumberOfPages()).isEqualTo(POSLYSHAITE_NUMBER_OF_PAGES);
        assertThat(books.get(1).getYearOfPublishing()).isEqualTo(POSLYSHAITE_YEAR_OF_PUBLISHING);
        assertThat(books.get(1).getAuthor().getSurname()).isEqualTo(POSLYSHAITE_AUTHOR_SURNAME);
        assertThat(books.get(1).getGenre().getName()).isEqualTo(POSLYSHAITE_GENRE);

        assertThat(books.get(2).getId()).isEqualTo(ANNA_KARENINA_ID);
        assertThat(books.get(2).getTitle()).isEqualTo(ANNA_KARENINA_TITLE);
        assertThat(books.get(2).getNumberOfPages()).isEqualTo(ANNA_KARENINA_NUMBER_OF_PAGES);
        assertThat(books.get(2).getYearOfPublishing()).isEqualTo(ANNA_KARENINA_YEAR_OF_PUBLISHING);
        assertThat(books.get(2).getAuthor().getSurname()).isEqualTo(ANNA_KARENINA_AUTHOR_SURNAME);
        assertThat(books.get(2).getGenre().getName()).isEqualTo(ANNA_KARENINA_GENRE);
    }
}