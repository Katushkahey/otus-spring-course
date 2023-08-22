package com.homework.spring.repository;

import com.homework.spring.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, Long> {

    Book save(Book book);

    Optional<Book> findById(String id);

    List<Book> findAll();

    List<Book> findByAuthors_Id(String authorId);

    void delete(Book book);

    void deleteByAuthors_Id(String id);

    void deleteByGenre_Id(String id);
}
