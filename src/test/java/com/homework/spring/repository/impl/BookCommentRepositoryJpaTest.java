package com.homework.spring.repository.impl;

import com.homework.spring.entity.Book;
import com.homework.spring.entity.BookComment;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA Repository for work with BookComments ")
@DataJpaTest
@Import(BookCommentRepositoryJpa.class)
public class BookCommentRepositoryJpaTest {

    private static final int INITIAL_NUMBER_OF_COMMENTS = 2;
    private static final int EXPECTED_NUMBER_OF_REQUESTS_TO_DB = 1;
    private static final Long ANNA_KARENINA_ID = 3L;
    private static final Long YCHEBNIK_PO_ALGEBRE_ID = 7L;
    private static final int NUMBER_OF_COMMENTS_TO_YCHEBNIK_PO_ALGEBRE = 2;
    private static final Long NEGATIVE_COMMENT_TO_YCHEBNIK_PO_ALGEBRE_ID = 1L;
    @Autowired
    private BookCommentRepositoryJpa bookCommentRepositoryJpa;
    @Autowired
    private TestEntityManager em;

    @DisplayName("must find all bookComments in database")
    @Test
    public void findAllBookComments() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<BookComment> books = bookCommentRepositoryJpa.findAll();
        assertThat(books.size()).isEqualTo(INITIAL_NUMBER_OF_COMMENTS);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_NUMBER_OF_REQUESTS_TO_DB);
    }

    @DisplayName("must create new BookComment in database")
    @Test
    public void addBookComment() {
        Book book = em.find(Book.class, ANNA_KARENINA_ID);
        BookComment bookComment = new BookComment(null, "i like it", "i recommend to everybody. The best book ever", book);
        bookCommentRepositoryJpa.save(bookComment);
        List<BookComment> bookComments = bookCommentRepositoryJpa.findAll();
        assertThat(bookComments.size()).isEqualTo(INITIAL_NUMBER_OF_COMMENTS + 1);
    }

    @DisplayName("must find BookComment in database by book id")
    @Test
    public void findBookById() {
        List<BookComment> foundComments = bookCommentRepositoryJpa.findByBookId(YCHEBNIK_PO_ALGEBRE_ID);
        assertThat(foundComments.size()).isEqualTo(NUMBER_OF_COMMENTS_TO_YCHEBNIK_PO_ALGEBRE);
    }

    @DisplayName("must delete BookComment in database by id; Must NOT delete Book")
    @Test
    public void deleteBookCommentById() {
        BookComment bookComment = em.find(BookComment.class, NEGATIVE_COMMENT_TO_YCHEBNIK_PO_ALGEBRE_ID);
        bookCommentRepositoryJpa.delete(bookComment);
        List<BookComment> foundComments = bookCommentRepositoryJpa.findByBookId(YCHEBNIK_PO_ALGEBRE_ID);
        assertThat(foundComments.size()).isEqualTo(NUMBER_OF_COMMENTS_TO_YCHEBNIK_PO_ALGEBRE - 1);
        Book book = em.find(Book.class, YCHEBNIK_PO_ALGEBRE_ID);
        assertThat(book).isNotNull();
    }
}
