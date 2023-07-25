package com.homework.spring.repository.impl;

import com.homework.spring.entity.Book;
import com.homework.spring.repository.BookRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            em.flush();
        } else {
            em.merge(book);
        }
        return book.getId();
    }

    @Override
    public Book findById(Long id) {
        EntityGraph<?> entityGraph = em.getEntityGraph("book_genre_graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.authors where b.id= :id", Book.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new RuntimeException("Book with id: " + id + " was not found");
        }
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book_genre_graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.authors", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public void delete(Book book) {
        em.remove(book);
    }
}
