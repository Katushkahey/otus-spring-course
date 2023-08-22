package com.homework.spring.repository;

import com.homework.spring.entity.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends MongoRepository<Author, Long> {

    Author save(Author author);

    Optional<Author> findById(String id);

    List<Author> findAll();

    Author findAuthorByNameAndSurnameAndFatherName(String name, String surname, String fatherName);

    void delete(Author author);
}
