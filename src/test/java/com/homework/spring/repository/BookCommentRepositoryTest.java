package com.homework.spring.repository;

import com.homework.spring.entity.Book;
import com.homework.spring.entity.BookComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"сom.homework.spring.config", "сom.homework.spring.repository"})
@DisplayName("Spring Data Repository for work with BookComments ")
public class BookCommentRepositoryTest {

    private static final int INITIAL_NUMBER_OF_COMMENTS = 2;
    private static final int NUMBER_OF_COMMENTS_TO_YCHEBNIK_PO_ALGEBRE = 2;
    public static final String NEW_COMMENT_TITLE = "i like it";
    public static final String NEW_COMMENT_TEXT = "i recommend to everybody. The best book ever";
    @Autowired
    private BookCommentRepository bookCommentRepository;
    @Autowired
    private BookRepository bookRepository;

    @DisplayName("must find all bookComments in database")
    @Test
    public void findAllBookComments() {
        List<BookComment> books = bookCommentRepository.findAll();
        assertThat(books.size()).isEqualTo(INITIAL_NUMBER_OF_COMMENTS);
    }

    @DisplayName("must create new BookComment in database")
    @Test
    public void addBookComment() {
        Book book = bookRepository.findAll().get(0);
        BookComment bookComment = new BookComment(null, NEW_COMMENT_TITLE, NEW_COMMENT_TEXT, book);
        bookCommentRepository.save(bookComment);
        List<BookComment> bookComments = bookCommentRepository.findAll();
        assertThat(bookComments.size()).isEqualTo(INITIAL_NUMBER_OF_COMMENTS + 1);

        bookComments = bookCommentRepository.findByBookId(book.getId());
        assertThat((int) bookComments.stream().filter(bc -> NEW_COMMENT_TITLE.equals(bc.getTitle()) &&
                NEW_COMMENT_TEXT.equals(bc.getText())).count()).isEqualTo(1);

    }

    @DisplayName("must find BookComment in database by book id")
    @Test
    public void findByBookId() {
        List<Book> books = bookRepository.findAll();
        Book ychebnikPoalgebre = books.get(books.size() - 1);
        List<BookComment> foundComments = bookCommentRepository.findByBookId(ychebnikPoalgebre.getId());
        assertThat(foundComments.size()).isEqualTo(NUMBER_OF_COMMENTS_TO_YCHEBNIK_PO_ALGEBRE);
    }

    @DisplayName("must delete BookComment in database by id; Must NOT delete Book")
    @Test
    public void deleteBookCommentById() {
        List<Book> books = bookRepository.findAll();
        int initialBooksSize = books.size();
        Book ychebnikPoalgebre = books.get(books.size() - 1);
        List<BookComment> foundComments = bookCommentRepository.findByBookId(ychebnikPoalgebre.getId());
        assertThat(foundComments.size()).isEqualTo(NUMBER_OF_COMMENTS_TO_YCHEBNIK_PO_ALGEBRE);

        bookCommentRepository.delete(foundComments.get(0));

        List<BookComment> foundCommentsAfterDelete = bookCommentRepository.findByBookId(ychebnikPoalgebre.getId());
        assertThat(foundCommentsAfterDelete.size()).isEqualTo(NUMBER_OF_COMMENTS_TO_YCHEBNIK_PO_ALGEBRE - 1);
        books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(initialBooksSize);
    }
}
