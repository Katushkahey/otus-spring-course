package com.homework.spring.service;

import com.homework.spring.entity.BookComment;
import com.homework.spring.repository.BookCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookCommentService {

    private final BookCommentRepository bookCommentRepository;

    public Long save(BookComment bookComment) {
        return bookCommentRepository.save(bookComment);
    }

    public List<BookComment> findByBookId(Long bookId) {
        return bookCommentRepository.findByBookId(bookId);
    }

    public void delete(BookComment bookComment) {
        bookCommentRepository.delete(bookComment);
    }

    public void deleteById(Long id) {
        BookComment comment = bookCommentRepository.findById(id);
        bookCommentRepository.delete(comment);
    }
}
