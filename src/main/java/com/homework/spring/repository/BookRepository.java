package com.homework.spring.repository;

import com.homework.spring.entity.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Book save(Book book);

    @EntityGraph(attributePaths = {"genre"})
    Optional<Book> findById(Long id);

    @EntityGraph(attributePaths = {"genre"})
    List<Book> findAll();

    void delete(Book book);
}
