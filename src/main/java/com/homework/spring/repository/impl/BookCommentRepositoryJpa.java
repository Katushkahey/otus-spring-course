package com.homework.spring.repository.impl;

import com.homework.spring.entity.BookComment;
import com.homework.spring.repository.BookCommentRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class BookCommentRepositoryJpa implements BookCommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long save(BookComment bookComment) {
        if (bookComment.getId() == null) {
            em.persist(bookComment);
            em.flush();
        } else {
            em.merge(bookComment);
        }
        return bookComment.getId();
    }

    @Override
    public List<BookComment> findByBookId(Long bookId) {
        TypedQuery<BookComment> query = em.createQuery("select c from BookComment c where c.book.id= :bookId ",
                BookComment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public void delete(BookComment bookComment) {
        em.remove(bookComment);
    }

    @Override
    public BookComment findById(Long id) {
        BookComment bookComment = em.find(BookComment.class, id);
        Optional<BookComment> autorOptional = Optional.ofNullable(bookComment);
        return autorOptional.orElseThrow(() -> new RuntimeException("BookComment with id: " + id + " was not found"));
    }

    @Override
    public List<BookComment> findAll() {
        TypedQuery<BookComment> query = em.createQuery("select c from BookComment c", BookComment.class);
        return query.getResultList();
    }
}
