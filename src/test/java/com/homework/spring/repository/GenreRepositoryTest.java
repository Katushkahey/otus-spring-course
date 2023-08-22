package com.homework.spring.repository;

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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Spring Data Repository for work with Genres ")
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"сom.homework.spring.config", "сom.homework.spring.repository"})
public class GenreRepositoryTest {

    private static final int INITIAL_NUMBER_OF_GENRES = 4;
    private static final String POETRY_NAME = "poetry";
    private static final String YCHEBNIK_NAME = "ychebnik";
    private static final String ZIV_NAME = "Boris";
    private static final String ZIV_SURNAME = "Ziv";
    private static final String ZIV_FATHER_NAME = "Germanovich";
    private static final String GOLDICH_NAME = "Vladimir";
    private static final String GOLDICH_SURNAME = "Goldich";
    public static final String GOLDICH_FATHER_NAME = "Anatolevich";

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookCommentRepository bookCommentRepository;

    @DisplayName("must find all genres in database")
    @Test
    public void findAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        assertThat(genres.size()).isEqualTo(INITIAL_NUMBER_OF_GENRES);
    }

    @DisplayName("must create new Genre in database")
    @Test
    public void addBookComment() {
        Genre genre = new Genre(null, "new genre");
        genreRepository.save(genre);
        List<Genre> bookComments = genreRepository.findAll();
        assertThat(bookComments.size()).isEqualTo(INITIAL_NUMBER_OF_GENRES + 1);
    }

    @DisplayName("must find Genre in database by id")
    @Test
    public void findGenreById() {
        Genre foundGenre = genreRepository.findGenreByName(POETRY_NAME);
        assertThat(foundGenre).isNotNull();
    }

    @DisplayName("must delete Genre in database by id. Must also delete Books with this genre and their comments. " +
            "Author should not be removed")
    @Test
    public void deleteGenreById() {
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

        genreRepository.delete(ychebnikGenre);

        Genre ychebnikGenreAfterDelete = genreRepository.findGenreByName(YCHEBNIK_NAME);
        assertThat(ychebnikGenreAfterDelete).isNull();

        List<Book> booksAfterGenreDelete = bookRepository.findAll();
        List<String> ychebnikiIdsAfterGenreDelete = booksAfterGenreDelete
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
    }
}
