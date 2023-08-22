package com.homework.spring.repository;

import com.homework.spring.entity.Author;
import com.homework.spring.entity.Book;
import com.homework.spring.entity.BookComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"сom.homework.spring.config", "сom.homework.spring.repository", "сom.homework.spring.changelogs"})
@DisplayName("Spring Data Mongo Repository for work with Authors ")
public class AuthorRepositoryTest {
    private static final String PUSHKIN_NAME = "Alexander";
    private static final String PUSHKIN_SURNAME = "Pushkin";
    private static final String PUSHKIN_FATHER_NAME = "Sergeevich";
    private static final String PUSHKIN_DATE_OF_BIRTH = "1799-05-26";
    private static final String ZIV_NAME = "Boris";
    private static final String ZIV_SURNAME = "Ziv";
    private static final String ZIV_FATHER_NAME = "Germanovich";
    private static final int INITIAL_NUMBER_OF_AUTHORS = 5;

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookCommentRepository bookCommentRepository;

    @DisplayName("must find all authors in database")
    @Test
    public void findAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        assertThat(authors.size()).isEqualTo(INITIAL_NUMBER_OF_AUTHORS);
    }

    @DisplayName("must create new Author in database")
    @Test
    public void addAuthor() {
        Author author = new Author(null, "Kate", "Ivanova", "Igorevna", "23-02-1996");
        authorRepository.save(author);
        List<Author> authors = authorRepository.findAll();
        assertThat(authors.size()).isEqualTo(INITIAL_NUMBER_OF_AUTHORS + 1);
    }

    @DisplayName("must find Author in database by id")
    @Test
    public void findAuthorById() {
        Author author = authorRepository.findAuthorByNameAndSurnameAndFatherName(PUSHKIN_NAME,
                PUSHKIN_SURNAME, PUSHKIN_FATHER_NAME);
        assertThat(author.getName()).isEqualTo(PUSHKIN_NAME);
        assertThat(author.getSurname()).isEqualTo(PUSHKIN_SURNAME);
        assertThat(author.getFatherName()).isEqualTo(PUSHKIN_FATHER_NAME);
        assertThat(author.getDateOfBirth()).isEqualTo(PUSHKIN_DATE_OF_BIRTH);
    }

    @DisplayName("must delete Author in database by id. Must also delete Books of this Author and their comments. " +
            "Genre should not be removed")
    @Test
    public void deleteGenreById() {
        Author ychebnikAuthor = authorRepository.findAuthorByNameAndSurnameAndFatherName(ZIV_NAME, ZIV_SURNAME, ZIV_FATHER_NAME);

        List<Book> ychebniks = bookRepository.findByAuthors_Id(ychebnikAuthor.getId());
        List<String> booksId = ychebniks.stream().map(Book::getId).collect(Collectors.toList());

        List<BookComment> allCommentsToAuthorsBooks = new ArrayList<>();

        for (Book book : ychebniks) {
            List<BookComment> comments = bookCommentRepository.findByBookId(book.getId());
            allCommentsToAuthorsBooks.addAll(comments);
        }
        List<String> commentsId = allCommentsToAuthorsBooks.stream().map(BookComment::getId).collect(Collectors.toList());

        assertThat(ychebnikAuthor).isNotNull();
        assertThat(ychebniks).isNotNull();
        assertThat(ychebniks.size()).isEqualTo(1);
        assertThat(allCommentsToAuthorsBooks.size()).isEqualTo(2);
        assertThat(genreRepository.findGenreByName("ychebnik")).isNotNull();

        authorRepository.delete(ychebnikAuthor);

        Author authorAfterDelete = authorRepository.findAuthorByNameAndSurnameAndFatherName(ZIV_NAME, ZIV_SURNAME, ZIV_FATHER_NAME);
        assertThat(authorAfterDelete).isNull();

        for (String bookId : booksId) {
            Optional<Book> book = bookRepository.findById(bookId);
            assertThat(book).isEmpty();
        }

        for (String bookCommentId : commentsId) {
            Optional<BookComment> bookComment = bookCommentRepository.findById(bookCommentId);
            assertThat(bookComment).isEmpty();
        }

        assertThat(genreRepository.findGenreByName("ychebnik")).isNotNull();
    }
}
