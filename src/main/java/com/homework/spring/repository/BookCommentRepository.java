package com.homework.spring.repository;

import com.homework.spring.entity.BookComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCommentRepository extends CrudRepository<BookComment, Long> {

    BookComment save(BookComment bookComment);

    List<BookComment> findByBookId(Long bookId);

    void delete(BookComment bookComment);

    Optional<BookComment> findById(Long id);

    List<BookComment> findAll();
}
