package com.homework.spring.service;

import com.homework.spring.entity.Genre;
import com.homework.spring.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GenreService {

    private final GenreRepository genreRepository;

    public long add(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre findById(long id) {
        return genreRepository.findById(id);
    }

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public void deleteById(Long id) {
        Genre genre = genreRepository.findById(id);
        genreRepository.delete(genre);
    }
}
