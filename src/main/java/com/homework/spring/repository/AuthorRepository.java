package com.homework.spring.repository;

import com.homework.spring.entity.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    Author save(Author author);

    Optional<Author> findById(Long id);

    List<Author> findAll();

    Author findAuthorByNameAndSurnameAndFatherName(String name, String surname, String fatherName);

    void delete(Author author);
}
