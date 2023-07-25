package com.homework.spring.service;

import com.homework.spring.entity.Book;
import com.homework.spring.mapper.BookMapper;
import com.homework.spring.repository.BookRepository;
import com.homework.spring.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.homework.spring.util.Util.isStringEmpty;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public long save(com.homework.spring.dto.Book book) {
        validateBook(book);
        Book bookEntity = bookMapper.toEntity(book);

        return bookRepository.save(bookEntity);
    }

    public void update(com.homework.spring.dto.Book book) {
        validateBook(book);
        Book bookEntity = bookMapper.toEntity(book);
        bookRepository.save(bookEntity);
    }

    public com.homework.spring.dto.Book findById(long id) {
        return bookMapper.toDto(bookRepository.findById(id));
    }

    public List<com.homework.spring.dto.Book> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    private void validateBook(com.homework.spring.dto.Book book) {
        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            if (book.getAuthors().stream().anyMatch(a -> Util.isStringEmpty(a) || a.split(" ").length < 3)) {
                throw new IllegalArgumentException("one of mandatory parameters (name, surname, fatherName, dateOfBirth) " +
                        "are missing for author");
            }
        } else {
            throw new IllegalArgumentException("author is not provided.");
        }
        if (book.getGenre() == null || isStringEmpty(book.getGenre())) {
            throw new IllegalArgumentException("genre is not provided for this book.");
        }
    }

    public void deleteById(Long id) {
        Book book = bookRepository.findById(id);
        bookRepository.delete(book);
    }
}
