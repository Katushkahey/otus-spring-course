package com.homework.spring.repository;

import com.homework.spring.entity.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {

    Genre save(Genre genre);

    Optional<Genre> findById(Long id);

    List<Genre> findAll();

    Genre findGenreByName(String name);

    void delete(Genre genre);
}
