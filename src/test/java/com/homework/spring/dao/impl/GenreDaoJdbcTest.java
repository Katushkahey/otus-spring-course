package com.homework.spring.dao.impl;

import com.homework.spring.entity.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    private static final Long POEM_ID = 1L;
    private static final String POEM_NAME = "poem";
    private static final Long POETRY_ID = 2L;
    private static final String POETRY_NAME = "poetry";
    private static final Long ROMAN_ID = 3L;
    private static final String ROMAN_NAME = "roman";
    private static final int INIT_NUMBER_OF_GENRES = 3;
    @Test
    void add() {
        Genre genre = new Genre();
        genre.setName("newGenre");
        Long id = genreDaoJdbc.add(genre);
        genre.setId(id);
        List<Genre> genres = genreDaoJdbc.getAll();
        assertThat(genres.size()).isEqualTo(INIT_NUMBER_OF_GENRES + 1);
        assertThat(genres.get(3)).isEqualTo(genre);
    }

    @Test
    void getById() {
        Genre genre = genreDaoJdbc.getById(1L);
        assertThat(genre.getId()).isEqualTo(POEM_ID);
        assertThat(genre.getName()).isEqualTo(POEM_NAME);
    }

    @Test
    void getAll() {
        List<Genre> genres = genreDaoJdbc.getAll();
        assertThat(genres.size()).isEqualTo(INIT_NUMBER_OF_GENRES);
        assertThat(genres.get(0).getId()).isEqualTo(POEM_ID);
        assertThat(genres.get(0).getName()).isEqualTo(POEM_NAME);
        assertThat(genres.get(1).getId()).isEqualTo(POETRY_ID);
        assertThat(genres.get(1).getName()).isEqualTo(POETRY_NAME);
        assertThat(genres.get(2).getId()).isEqualTo(ROMAN_ID);
        assertThat(genres.get(2).getName()).isEqualTo(ROMAN_NAME);
    }

    @Test
    void getGenreIdByName() {
        Long id = genreDaoJdbc.getGenreIdByName(POEM_NAME);
        assertThat(id).isEqualTo(POEM_ID);
    }
}