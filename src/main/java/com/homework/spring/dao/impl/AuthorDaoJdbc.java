package com.homework.spring.dao.impl;

import com.homework.spring.dao.AuthorDao;
import com.homework.spring.entity.Author;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    public Author getById(long id) {
        return namedParameterJdbcOperations.queryForObject("select * from AUTHOR where id= :id", Map.of("id", id), new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query("select * from AUTHOR", new AuthorMapper());
    }

    @Override
    public Long getAuthorIdByNameSurnameFatherNameAndDateOfBirth(String name, String surname, String fatherName,
                                                                 String dateOfBirth) {
        try {
            return namedParameterJdbcOperations.queryForObject("select id from AUTHOR where name = :name and " +
                            "surname = :surname and father_name = :fatherName and date_of_birth = :dateOfBirth",
                    Map.of("name", name, "surname", surname, "fatherName", fatherName, "dateOfBirth", dateOfBirth),
                    Long.class);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    private class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author();
            author.setId(rs.getLong("ID"));
            author.setName(rs.getString("NAME"));
            author.setSurname(rs.getString("SURNAME"));
            author.setFatherName(rs.getString("FATHER_NAME"));
            author.setDateOfBirth(rs.getString("DATE_OF_BIRTH"));

            return author;
        }
    }
}
