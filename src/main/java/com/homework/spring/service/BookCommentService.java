package com.homework.spring.service;

import com.homework.spring.entity.BookComment;
import com.homework.spring.repository.BookCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookCommentService {

    private final BookCommentRepository bookCommentRepository;

    @Transactional
    public Long save(BookComment bookComment) {
        return bookCommentRepository.save(bookComment).getId();
    }

    @Transactional(readOnly = true)
    public List<BookComment> findByBookId(Long bookId) {
        return bookCommentRepository.findByBookId(bookId);
    }

    @Transactional
    public void delete(BookComment bookComment) {
        bookCommentRepository.delete(bookComment);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<BookComment> comment = bookCommentRepository.findById(id);
        comment.ifPresent(bookCommentRepository::delete);
    }
}
