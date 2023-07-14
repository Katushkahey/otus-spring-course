package com.homework.spring.dao.impl;

import com.homework.spring.dao.GenreDao;
import com.homework.spring.entity.Genre;
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
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public long add(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("insert into GENRE(name) values (:name)", params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Genre getById(Long id) {
        return namedParameterJdbcOperations.queryForObject("select * from GENRE where id= :id", Map.of("id", id),
                new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcOperations.query("select * from GENRE", new GenreMapper());
    }

    @Override
    public Long getGenreIdByName(String name) {
        try {
            return namedParameterJdbcOperations.queryForObject("select id from GENRE where name= :name",
                    Map.of("name", name), Long.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre genre = new Genre();
            genre.setId(rs.getLong("ID"));
            genre.setName(rs.getString("NAME"));

            return genre;
        }
    }
}
