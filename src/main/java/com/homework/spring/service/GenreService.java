package com.homework.spring.service;

import com.homework.spring.entity.Genre;
import com.homework.spring.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    @Transactional
    public String add(Genre genre) {
        return genreRepository.save(genre).getId();
    }

    @Transactional(readOnly = true)
    public Genre findById(String id) {
        return genreRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Transactional
    public void deleteById(String id) {
        Optional<Genre> genre = genreRepository.findById(id);
        genre.ifPresent(genreRepository::delete);
    }
}
