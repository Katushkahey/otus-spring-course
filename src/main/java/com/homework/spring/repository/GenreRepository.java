package com.homework.spring.repository;

import com.homework.spring.entity.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends MongoRepository<Genre, Long> {

    Genre save(Genre genre);

    Optional<Genre> findById(String id);

    List<Genre> findAll();

    Genre findGenreByName(String name);

    void delete(Genre genre);
}
