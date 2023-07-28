package com.homework.spring.service;

import com.homework.spring.entity.Author;
import com.homework.spring.mapper.AuthorMapper;
import com.homework.spring.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    
    @Transactional
    public long add(com.homework.spring.dto.Author author) {
        Author authorEntity = authorMapper.toEntity(author);
        return authorRepository.save(authorEntity);
    }
    @Transactional(readOnly = true)
    public com.homework.spring.dto.Author findById(Long id) {
        Author author = authorRepository.findById(id);
        return authorMapper.toDto(author);
    }

    @Transactional(readOnly = true)
    public List<com.homework.spring.dto.Author> findAll() {
        return authorRepository.findAll().stream().map(authorMapper::toDto).collect(Collectors.toList());
    }
    
    @Transactional
    public void deleteById(Long id) {
        Author author = authorRepository.findById(id);
        authorRepository.delete(author);
    }
}
