package com.homework.spring.dao.impl;

import com.homework.spring.dao.BookDao;
import com.homework.spring.entity.Author;
import com.homework.spring.entity.Book;
import com.homework.spring.entity.Genre;
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
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public long add(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("numberOfPages", book.getNumberOfPages());
        params.addValue("yearOfPublishing", book.getYearOfPublishing());
        params.addValue("authorId", book.getAuthor().getId());
        params.addValue("genreId", book.getGenre().getId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update("insert into BOOK(title, number_of_pages, year_of_publishing, author_id, " +
                "genre_id) values (:title, :numberOfPages, :yearOfPublishing, :authorId, :genreId)", params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Book book) {

        namedParameterJdbcOperations.update("update BOOK set title = :title, number_of_pages = :numberOfPages, " +
                "year_of_publishing = :yearOfPublishing, author_id = :authorId, genre_id = :genreId",
                Map.of("title", book.getTitle(), "numberOfPages", book.getNumberOfPages(),
                        "yearOfPublishing", book.getYearOfPublishing(), "authorId", book.getAuthor().getId(),
                        "genreId", book.getGenre().getId()));
    }

    @Override
    public Book getById(long id) {
        return namedParameterJdbcOperations.queryForObject("select b.id, b.title, b.number_of_pages," +
                        "b.year_of_publishing, a.id as author_id, a.name as author_name, a.surname as author_surname, " +
                        "a.father_name as author_father_name, a.date_of_birth as author_date_of_birth, " +
                        "g.id as genre_id, g.name as genre_name from " +
                        "BOOK b join AUTHOR a on b.author_id = a.id join GENRE g on b.genre_id = g.id" +
                        " where b.id= :id", Map.of("id", id),
                new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query("select b.id, b.title, b.number_of_pages," +
                "b.year_of_publishing, a.id as author_id, a.name as author_name, a.surname as author_surname, " +
                "a.father_name as author_father_name, a.date_of_birth as author_date_of_birth, " +
                "g.id as genre_id, g.name as genre_name from " +
                "BOOK b join AUTHOR a on b.author_id = a.id join GENRE g on b.genre_id = g.id", new BookMapper());
    }

    private class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getLong("ID"));
            book.setTitle(rs.getString("TITLE"));
            book.setNumberOfPages(rs.getInt("NUMBER_OF_PAGES"));
            book.setYearOfPublishing(rs.getInt("YEAR_OF_PUBLISHING"));

            Author author = new Author();
            author.setId(rs.getLong("AUTHOR_ID"));
            author.setName(rs.getString("AUTHOR_NAME"));
            author.setSurname(rs.getString("AUTHOR_SURNAME"));
            author.setFatherName(rs.getString("AUTHOR_FATHER_NAME"));
            author.setDateOfBirth(rs.getString("AUTHOR_DATE_OF_BIRTH"));

            book.setAuthor(author);

            Genre genre = new Genre();
            genre.setId(rs.getLong("GENRE_ID"));
            genre.setName(rs.getString("GENRE_NAME"));

            book.setGenre(genre);

            return book;
        }
    }
}
