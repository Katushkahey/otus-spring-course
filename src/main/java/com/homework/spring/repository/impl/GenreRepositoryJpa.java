package com.homework.spring.repository.impl;

import com.homework.spring.entity.Genre;
import com.homework.spring.repository.GenreRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long save(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
            em.flush();
        } else {
            em.merge(genre);
        }
        return genre.getId();
    }

    @Override
    public Genre findById(Long id) {
        Genre genre = em.find(Genre.class, id);
        Optional<Genre> genreOptional = Optional.ofNullable(genre);
        return genreOptional.orElseThrow(() -> new RuntimeException("Genre with id: " + id + " was not found"));
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public Genre findGenreByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name= :name ", Genre.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void delete(Genre genre) {
        em.remove(genre);
    }
}
