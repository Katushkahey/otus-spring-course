package com.homework.spring.service;

import com.homework.spring.entity.Author;
import com.homework.spring.entity.Book;
import com.homework.spring.mapper.AuthorMapper;
import com.homework.spring.repository.AuthorRepository;
import com.homework.spring.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;

    @Transactional
    public String add(com.homework.spring.dto.Author author) {
        Author authorEntity = authorMapper.toEntity(author);
        return authorRepository.save(authorEntity).getId();
    }

    @Transactional(readOnly = true)
    public com.homework.spring.dto.Author findById(String id) {
        Author author = authorRepository.findById(id).orElseThrow();
        List<String> books = bookRepository.findByAuthors_Id(id)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
        return authorMapper.toDto(author, books);
    }

    @Transactional(readOnly = true)
    public List<com.homework.spring.dto.Author> findAll() {
        return authorRepository.findAll().stream().map(author -> {
            List<String> books = bookRepository.findByAuthors_Id(author.getId())
                    .stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList());
            return authorMapper.toDto(author, books);
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(String id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            authorRepository.delete(author.get());
        }

    }
}
