package com.homework.spring.dao.impl;

import com.homework.spring.entity.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;
    private static final int INIT_NUMBER_OF_AUTHORS = 3;
    private static final Long PUSHKIN_ID = 1L;
    private static final String PUSHKIN_NAME = "Alexander";
    private static final String PUSHKIN_SURNAME = "Pushkin";
    private static final String PUSHKIN_FATHER_NAME = "Sergeevich";
    private static final String PUSHKIN_DATE_OF_BIRTH = ("1799-05-26");
    private static final Long MAYAKOVSKIY_ID = 2L;
    private static final String MAYAKOVSKIY_NAME = "Vlodimir";
    private static final String MAYAKOVSKIY_SURNAME = "Mayakovskiy";
    private static final String MAYAKOVSKIY_FATHER_NAME = "Vlodimirovich";
    private static final String MAYAKOVSKIY_DATE_OF_BIRTH = "1893-07-07";
    private static final Long TOLSTOI_ID = 3L;
    private static final String TOLSTOI_NAME = "Lew";
    private static final String TOLSTOI_SURNAME = "Tolstoi";
    private static final String TOLSTOI_FATHER_NAME = "Nikolaevich";
    private static final String TOLSTOI_DATE_OF_BIRTH = "1828-08-28";


    @Test
    void addAuthor() {
        Author author = new Author();
        author.setName("nameNewAuthor");
        author.setSurname("surnameNewAuthor");
        author.setFatherName("fatherNameNewAuthor");
        author.setDateOfBirth("2023-07-04");
        Long id = authorDaoJdbc.add(author);
        author.setId(id);
        List<Author> authors = authorDaoJdbc.getAll();
        assertThat(authors.size()).isEqualTo(INIT_NUMBER_OF_AUTHORS + 1);
        assertThat(authors.get(3)).isEqualTo(author);
    }

    @Test
    void getAuthorById() {
        Author author = authorDaoJdbc.getById(1L);
        assertThat(author.getId()).isEqualTo(PUSHKIN_ID);
        assertThat(author.getName()).isEqualTo(PUSHKIN_NAME);
        assertThat(author.getSurname()).isEqualTo(PUSHKIN_SURNAME);
        assertThat(author.getFatherName()).isEqualTo(PUSHKIN_FATHER_NAME);
        assertThat(author.getDateOfBirth()).isEqualTo(PUSHKIN_DATE_OF_BIRTH);
    }

    @Test
    void getAuthorIdByNameSurnameFatherNameAndDateOfBirth() {
        Long id = authorDaoJdbc.getAuthorIdByNameSurnameFatherNameAndDateOfBirth(PUSHKIN_NAME, PUSHKIN_SURNAME,
                PUSHKIN_FATHER_NAME, PUSHKIN_DATE_OF_BIRTH);
        assertThat(id).isEqualTo(PUSHKIN_ID);
    }

    @Test
    void getAllAuthors() {
        List<Author> authors = authorDaoJdbc.getAll();
        assertThat(authors.size()).isEqualTo(INIT_NUMBER_OF_AUTHORS);

        assertThat(authors.get(0).getId()).isEqualTo(PUSHKIN_ID);
        assertThat(authors.get(0).getName()).isEqualTo(PUSHKIN_NAME);
        assertThat(authors.get(0).getSurname()).isEqualTo(PUSHKIN_SURNAME);
        assertThat(authors.get(0).getFatherName()).isEqualTo(PUSHKIN_FATHER_NAME);
        assertThat(authors.get(0).getDateOfBirth()).isEqualTo(PUSHKIN_DATE_OF_BIRTH);

        assertThat(authors.get(1).getId()).isEqualTo(MAYAKOVSKIY_ID);
        assertThat(authors.get(1).getName()).isEqualTo(MAYAKOVSKIY_NAME);
        assertThat(authors.get(1).getSurname()).isEqualTo(MAYAKOVSKIY_SURNAME);
        assertThat(authors.get(1).getFatherName()).isEqualTo(MAYAKOVSKIY_FATHER_NAME);
        assertThat(authors.get(1).getDateOfBirth()).isEqualTo(MAYAKOVSKIY_DATE_OF_BIRTH);

        assertThat(authors.get(2).getId()).isEqualTo(TOLSTOI_ID);
        assertThat(authors.get(2).getName()).isEqualTo(TOLSTOI_NAME);
        assertThat(authors.get(2).getSurname()).isEqualTo(TOLSTOI_SURNAME);
        assertThat(authors.get(2).getFatherName()).isEqualTo(TOLSTOI_FATHER_NAME);
        assertThat(authors.get(2).getDateOfBirth()).isEqualTo(TOLSTOI_DATE_OF_BIRTH);
    }
}