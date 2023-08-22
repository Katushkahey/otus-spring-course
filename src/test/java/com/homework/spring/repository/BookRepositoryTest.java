package com.homework.spring.repository;

import com.homework.spring.entity.Author;
import com.homework.spring.entity.Book;
import com.homework.spring.entity.BookComment;
import com.homework.spring.entity.Genre;
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

@DisplayName("Spring Data Repository for work with Books ")
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"сom.homework.spring.config", "сom.homework.spring.repository"})
public class BookRepositoryTest {
    private static final int INITIAL_NUMBER_OF_BOOKS = 7;
    private static final String YCHEBNIK_NAME = "ychebnik";
    private static final String ZIV_NAME = "Boris";
    private static final String ZIV_SURNAME = "Ziv";
    private static final String ZIV_FATHER_NAME = "Germanovich";
    private static final String GOLDICH_NAME = "Vladimir";
    private static final String GOLDICH_SURNAME = "Goldich";
    public static final String GOLDICH_FATHER_NAME = "Anatolevich";
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookCommentRepository bookCommentRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("must find all books in database")
    @Test
    public void findAllBook() {
        List<Book> books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(INITIAL_NUMBER_OF_BOOKS);
    }

    @DisplayName("must create new Book in database")
    @Test
    public void addBook() {
        Author author = authorRepository.findAll().get(0);
        Genre genre = genreRepository.findAll().get(0);
        Book book = new Book(null, "new Title", 100, 2023, List.of(author), genre);
        bookRepository.save(book);
        List<Book> books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(INITIAL_NUMBER_OF_BOOKS + 1);
    }

    @DisplayName("must find Book in database by id")
    @Test
    public void findBookById() {
        Book firstBook = bookRepository.findAll().get(0);
        Optional<Book> bookById = bookRepository.findById(firstBook.getId());
        assertThat(bookById).isPresent();
        assertThat(bookById.get()).isEqualTo(firstBook);
    }

    @DisplayName("must delete Book in database by id; Together with book it should remove all book comments. " +
            "It should NOT remove book Author and should NOT remove book genre")
    @Test
    public void deleteBookById() {
        Genre ychebnikGenre = genreRepository.findGenreByName(YCHEBNIK_NAME);
        assertThat(ychebnikGenre).isNotNull();

        List<Book> books = bookRepository.findAll();
        List<String> ychebnikiIds = books
                .stream()
                .filter(b -> YCHEBNIK_NAME.equals(b.getGenre().getName()))
                .map(Book::getId)
                .collect(Collectors.toList());
        assertThat(ychebnikiIds.size()).isEqualTo(1);

        List<BookComment> ychebnikiComments = new ArrayList<>();
        ychebnikiIds.forEach(id -> {
            ychebnikiComments.addAll(bookCommentRepository.findByBookId(id));
        });
        assertThat(ychebnikiComments.size()).isEqualTo(2);

        assertThat(authorRepository.findAuthorByNameAndSurnameAndFatherName(ZIV_NAME, ZIV_SURNAME,
                ZIV_FATHER_NAME)).isNotNull();
        assertThat(authorRepository.findAuthorByNameAndSurnameAndFatherName(GOLDICH_NAME, GOLDICH_SURNAME,
                GOLDICH_FATHER_NAME)).isNotNull();

        bookRepository.delete(books.get(0));

        List<Book> booksAfterDelete = bookRepository.findAll();
        List<String> ychebnikiIdsAfterGenreDelete = booksAfterDelete
                .stream()
                .filter(b -> YCHEBNIK_NAME.equals(b.getGenre().getName()))
                .map(Book::getId)
                .collect(Collectors.toList());
        assertThat(ychebnikiIdsAfterGenreDelete.size()).isEqualTo(0);

        assertThat(bookCommentRepository.findAll()).isEmpty();

        assertThat(authorRepository.findAuthorByNameAndSurnameAndFatherName(ZIV_NAME, ZIV_SURNAME,
                ZIV_FATHER_NAME)).isNotNull();
        assertThat(authorRepository.findAuthorByNameAndSurnameAndFatherName(GOLDICH_NAME, GOLDICH_SURNAME,
                GOLDICH_FATHER_NAME)).isNotNull();

        assertThat(genreRepository.findGenreByName(YCHEBNIK_NAME)).isNotNull();
    }
}
