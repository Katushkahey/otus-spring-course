package com.homework.spring.repository.impl;

import com.homework.spring.entity.Author;
import com.homework.spring.entity.Book;
import com.homework.spring.entity.BookComment;
import com.homework.spring.entity.Genre;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA Repository for work with Authors ")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
public class AuthorRepositoryJpaTest {
    private static final String PUSHKIN_NAME = "Alexander";
    private static final String PUSHKIN_SURNAME = "Pushkin";
    private static final String PUSHKIN_FATHER_NAME = "Sergeevich";
    private static final String PUSHKIN_DATE_OF_BIRTH = "1799-05-26";
    private static final int NUMBER_OF_PUSHKIN_BOOKS_IN_DB = 2;
    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;
    @Autowired
    private TestEntityManager em;
    private static final int INITIAL_NUMBER_OF_AUTHORS = 5;
    private static final int EXPECTED_NUMBER_OF_REQUESTS_TO_DB = 2;
    private static final Long PUSHKIN_ID = 1L;
    private static final Long YCHEBNIK_ID = 7L;
    private static final Long YCHEBNIK_AUTHOR_ID = 4L;
    private static final Long YCHEBNIK_GENRE_ID = 4L;
    private static final List<Long> YCHEBNIK_COMMENT_ID_LIST = List.of(1L, 2L);

    @DisplayName("must find all authors in database")
    @Test
    public void findAllAuthors() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Author> authors = authorRepositoryJpa.findAll();
        assertThat(authors.size()).isEqualTo(INITIAL_NUMBER_OF_AUTHORS);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_NUMBER_OF_REQUESTS_TO_DB);
    }

    @DisplayName("must create new Author in database")
    @Test
    public void addAuthor() {
        Author author = new Author(null, "Kate", "Ivanova", "Igorevna", "23-02-1996", null);
        authorRepositoryJpa.save(author);
        List<Author> authors = authorRepositoryJpa.findAll();
        assertThat(authors.size()).isEqualTo(INITIAL_NUMBER_OF_AUTHORS + 1);
    }

    @DisplayName("must find Author in database by id")
    @Test
    public void findAuthorById() {
        Author author = authorRepositoryJpa.findById(PUSHKIN_ID);
        assertThat(author.getId()).isEqualTo(PUSHKIN_ID);
        assertThat(author.getName()).isEqualTo(PUSHKIN_NAME);
        assertThat(author.getSurname()).isEqualTo(PUSHKIN_SURNAME);
        assertThat(author.getFatherName()).isEqualTo(PUSHKIN_FATHER_NAME);
        assertThat(author.getDateOfBirth()).isEqualTo(PUSHKIN_DATE_OF_BIRTH);
        assertThat(author.getBooks().size()).isEqualTo(NUMBER_OF_PUSHKIN_BOOKS_IN_DB);
    }

    @DisplayName("must delete Author in database by id. Must also delete Books of this Author and their comments. " +
            "Genre should not be removed")
    @Test
    public void deleteGenreById() {
        Author ychebnikAuthor = em.find(Author.class, YCHEBNIK_AUTHOR_ID);
        authorRepositoryJpa.delete(ychebnikAuthor);

        Author authorAfterDelete = em.find(Author.class, YCHEBNIK_ID);
        assertThat(authorAfterDelete).isNull();

        assertThat(em.find(Book.class, YCHEBNIK_ID)).isNull();

        for (Long commentId : YCHEBNIK_COMMENT_ID_LIST) {
            assertThat(em.find(BookComment.class, commentId)).isNull();
        }

        assertThat(em.find(Genre.class, YCHEBNIK_GENRE_ID)).isNotNull();

    }
}
