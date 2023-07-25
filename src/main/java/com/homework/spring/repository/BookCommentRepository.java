package com.homework.spring.repository;

import com.homework.spring.entity.BookComment;

import java.util.List;

public interface BookCommentRepository {

    Long save(BookComment bookComment);

    List<BookComment> findByBookId(Long bookId);

    void delete(BookComment bookComment);

    BookComment findById(Long id);

    List<BookComment> findAll();
}
