package com.homework.spring.events;

import com.homework.spring.entity.Author;
import com.homework.spring.entity.Book;
import com.homework.spring.repository.AuthorRepository;
import com.homework.spring.repository.BookCommentRepository;
import com.homework.spring.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MongoBookCascadeEventListener extends AbstractMongoEventListener<Book> {

    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    private final BookCommentRepository bookCommentRepository;

    @Override
    public void onBeforeConvert(@NotNull BeforeConvertEvent<Book> event) {
        super.onBeforeConvert(event);
        Book book = event.getSource();
        if (book.getGenre() != null) {
            book.setGenre(genreRepository.save(book.getGenre()));
        }
        if (book.getAuthors() != null) {
            book.getAuthors().stream()
                    .filter(author -> Objects.isNull(author.getId()))
                    .forEach(author -> {
                        Author newAuthor = authorRepository.save(author);
                        author.setId(newAuthor.getId());
                    });
        }
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        super.onBeforeDelete(event);
        String bookId = null;
        if (event.getSource().containsKey("_id")) {
            bookId = event.getSource().get("_id").toString();
        } else if (event.getSource().containsKey("authors._id")) {
            bookId = event.getSource().get("authors._id").toString();
        } else if (event.getSource().containsKey("genres._id")) {
            bookId = event.getSource().get("genres._id").toString();
        }
        if (bookId != null) {
            bookCommentRepository.deleteByBook_Id(bookId);
        }
    }
}
