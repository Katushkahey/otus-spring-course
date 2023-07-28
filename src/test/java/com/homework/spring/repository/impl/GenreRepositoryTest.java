package com.homework.spring.repository.impl;

import com.homework.spring.entity.Author;
import com.homework.spring.entity.Book;
import com.homework.spring.entity.BookComment;
import com.homework.spring.entity.Genre;
import com.homework.spring.repository.GenreRepository;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Spring Data Repository for work with Genres ")
@DataJpaTest
public class GenreRepositoryTest {

    private static final int INITIAL_NUMBER_OF_GENRES = 4;
    private static final int EXPECTED_NUMBER_OF_REQUESTS_TO_DB = 1;
    private static final long POETRY_ID = 2L;
    private static final String POETRY_NAME = "poetry";
    private static final Long YCHEBNIK_BOOK_ID = 7L;
    private static final List<Long> YCHEBNIK_COMMENT_ID_LIST = List.of(1L, 2L);
    private static final List<Long> YCHEBNIK_AUTHOR_ID_LIST = List.of(4L, 5L);
    private static final long YCHEBNIK_ID = 4L;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("must find all genres in database")
    @Test
    public void findAllGenres() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Genre> books = genreRepository.findAll();
        assertThat(books.size()).isEqualTo(INITIAL_NUMBER_OF_GENRES);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_NUMBER_OF_REQUESTS_TO_DB);
    }

    @DisplayName("must create new Genre in database")
    @Test
    public void addBookComment() {
        Genre genre = new Genre(null, "new genre", null);
        genreRepository.save(genre);
        List<Genre> bookComments = genreRepository.findAll();
        assertThat(bookComments.size()).isEqualTo(INITIAL_NUMBER_OF_GENRES + 1);
    }

    @DisplayName("must find Genre in database by id")
    @Test
    public void findGenreById() {
        Optional<Genre> foundGenre = genreRepository.findById(POETRY_ID);
        assert (foundGenre.isPresent());
        assertThat(foundGenre.get().getName()).isEqualTo(POETRY_NAME);
    }

    @DisplayName("must delete Genre in database by id. Must also delete Books with this genre and their comments. " +
            "Author should not be removed")
    @Test
    public void deleteGenreById() {
        Genre genre = em.find(Genre.class, YCHEBNIK_ID);
        genreRepository.delete(genre);
        Genre genreAfterDelete = em.find(Genre.class, YCHEBNIK_ID);
        assertThat(genreAfterDelete).isNull();
        assertThat(em.find(Book.class, YCHEBNIK_BOOK_ID)).isNull();
        for (Long commentId : YCHEBNIK_COMMENT_ID_LIST) {
            assertThat(em.find(BookComment.class, commentId)).isNull();
        }
        for (Long authorId : YCHEBNIK_AUTHOR_ID_LIST) {
            assertThat(em.find(Author.class, authorId)).isNotNull();
        }

    }
}
