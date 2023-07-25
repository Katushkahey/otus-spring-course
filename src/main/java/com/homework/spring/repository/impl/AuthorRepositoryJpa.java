package com.homework.spring.repository.impl;

import com.homework.spring.entity.Author;
import com.homework.spring.repository.AuthorRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long save(Author author) {
        if (author.getId() == null) {
            em.persist(author);
            em.flush();
        } else {
            em.merge(author);
        }
        return author.getId();
    }

    @Override
    public Author findById(Long id) {
        Author author = em.find(Author.class, id);
        Optional<Author> autorOptional = Optional.ofNullable(author);
        return autorOptional.orElseThrow(() -> new RuntimeException("Author with id: " + id + " was not found"));
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public Author findAuthorByNameSurnameFatherName(String name, String surname, String fatherName) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.name= :name and a.surname= :surname" +
                " and a.fatherName= :fatherName", Author.class);
        query.setParameter("name", name);
        query.setParameter("surname", surname);
        query.setParameter("fatherName", fatherName);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void delete(Author author) {
        em.remove(author);
    }
}
