package com.homework.spring.repository.impl;

import com.homework.spring.entity.Author;
import com.homework.spring.entity.Book;
import com.homework.spring.entity.BookComment;
import com.homework.spring.entity.Genre;
import com.homework.spring.repository.BookCommentRepository;
import com.homework.spring.repository.BookRepository;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Spring Data Repository for work with Books ")
@DataJpaTest
public class BookRepositoryTest {

    private static final Long PUSHKIN_ID = 1L;
    private static final Long POETRY_ID = 2L;
    private static final int INITIAL_NUMBER_OF_BOOKS = 7;
    private static final int EXPECTED_NUMBER_OF_REQUESTS_TO_DB = 1;
    private static final Long ANNA_KARENINA_ID = 3L;
    private static final Long YCHEBNIK_ID = 7L;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookCommentRepository bookCommentRepository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("must find all books in database")
    @Test
    public void findAllBook() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Book> books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(INITIAL_NUMBER_OF_BOOKS);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_NUMBER_OF_REQUESTS_TO_DB);
    }

    @DisplayName("must create new Book in database")
    @Test
    public void addBook() {
        Author author = em.find(Author.class, PUSHKIN_ID);
        Genre genre = em.find(Genre.class, POETRY_ID);
        Book book = new Book(null, "new Title", 100, 2023, List.of(author), genre, null);
        bookRepository.save(book);
        List<Book> books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(INITIAL_NUMBER_OF_BOOKS + 1);
    }

    @DisplayName("must find Book in database by id")
    @Test
    public void findBookById() {
        Book book = em.find(Book.class, ANNA_KARENINA_ID);
        Optional<Book> foundBook = bookRepository.findById(ANNA_KARENINA_ID);
        assert (foundBook.isPresent());
        assertThat(book).isEqualTo(foundBook.get());
    }

    @DisplayName("must delete Book in database by id; Together with book it should remove all book comments. " +
            "It should NOT remove book Author and should NOT remove book genre")
    @Test
    public void deleteBookById() {
        Book book = em.find(Book.class, YCHEBNIK_ID);
        List<Author> ychebnikAuthors = book.getAuthors().subList(0, book.getAuthors().size());
        Genre ychebnikGenre = book.getGenre();
        List<BookComment> ychebnikComments = bookCommentRepository.findByBookId(YCHEBNIK_ID);

        bookRepository.delete(book);

        assertThat(bookRepository.findAll().size()).isEqualTo(INITIAL_NUMBER_OF_BOOKS - 1);

        for (int i = 0; i < ychebnikAuthors.size(); i++) {
            Author author = em.find(Author.class, ychebnikAuthors.get(i).getId());
            assertThat(author).isNotNull();
        }

        Genre genre = em.find(Genre.class, ychebnikGenre.getId());
        assertThat(genre).isNotNull();

        for (BookComment ychebnikPoAlgebreComment : ychebnikComments) {
            BookComment comment = em.find(BookComment.class, ychebnikPoAlgebreComment.getId());
            assertThat(comment).isNull();
        }
    }
}
