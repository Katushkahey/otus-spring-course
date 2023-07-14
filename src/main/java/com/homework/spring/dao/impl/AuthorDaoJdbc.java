package com.homework.spring.dao.impl;

import com.homework.spring.dao.AuthorDao;
import com.homework.spring.entity.Author;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public long add(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", author.getName());
        params.addValue("surname", author.getSurname());
        params.addValue("fatherName", author.getFatherName());
        params.addValue("dateOfBirth", author.getDateOfBirth());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("insert into AUTHOR(name, surname, father_name, date_of_birth) values " +
                "(:name, :surname, :fatherName, :dateOfBirth)", params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public com.homework.spring.dto.Author getById(long id) {
        Map<Long, com.homework.spring.dto.Author> map = namedParameterJdbcOperations.query("select a.id, " +
                        "a.name, a.surname, a.father_name, a.date_of_birth, b.title as book_title from AUTHOR a left join " +
                        "Book b on a.id = b.author_id where a.id= :id",
                Map.of("id", id), new AutorResultSetExtractor());
        return map == null || map.isEmpty() ? null : map.get(id);
    }


    @Override
    public List<com.homework.spring.dto.Author> getAll() {
        Map<Long, com.homework.spring.dto.Author> map = namedParameterJdbcOperations.query("select a.id, a.name, " +
                "a.surname, a.father_name, a.date_of_birth, b.title as book_title from AUTHOR a left join Book b " +
                "on a.id = b.author_id", new AutorResultSetExtractor());
        return map == null || map.isEmpty() ? new ArrayList<>() : new ArrayList<>(map.values());
    }

    @Override
    public Long getAuthorIdByNameSurnameFatherNameAndDateOfBirth(String name, String surname, String fatherName,
                                                                 String dateOfBirth) {
        try {
            return namedParameterJdbcOperations.queryForObject("select id from AUTHOR where name = :name and " +
                            "surname = :surname and father_name = :fatherName and date_of_birth = :dateOfBirth",
                    Map.of("name", name, "surname", surname, "fatherName", fatherName, "dateOfBirth", dateOfBirth),
                    Long.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private class AutorResultSetExtractor implements ResultSetExtractor<Map<Long, com.homework.spring.dto.Author>> {

        @Override
        public Map<Long, com.homework.spring.dto.Author> extractData(ResultSet rs) throws SQLException {
            Map<Long, com.homework.spring.dto.Author> authors = new HashMap<>();
            while (rs.next()) {
                Long id = rs.getLong("ID");
                com.homework.spring.dto.Author author = authors.get(id);
                if (author == null) {
                    author = new com.homework.spring.dto.Author(id, rs.getString("NAME"),
                            rs.getString("SURNAME"), rs.getString("FATHER_NAME"),
                            rs.getString("DATE_OF_BIRTH"), new ArrayList<>());
                    authors.put(id, author);
                }
                author.getBooks().add(rs.getString("BOOK_TITLE"));
            }
            return authors;
        }
    }
}
