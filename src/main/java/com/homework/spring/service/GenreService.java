package com.homework.spring.service;

import com.homework.spring.entity.Genre;
import com.homework.spring.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    @Transactional
    public long add(Genre genre) {
        return genreRepository.save(genre);
    }

    @Transactional(readOnly = true)
    public Genre findById(long id) {
        return genreRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        Genre genre = genreRepository.findById(id);
        genreRepository.delete(genre);
    }
}
