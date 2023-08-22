package com.homework.spring.repository;

import com.homework.spring.entity.BookComment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCommentRepository extends MongoRepository<BookComment, Long> {

    BookComment save(BookComment bookComment);

    List<BookComment> findByBookId(String bookId);

    void delete(BookComment bookComment);

    void deleteByBook_Id(String bookId);

    Optional<BookComment> findById(String id);

    List<BookComment> findAll();
}
