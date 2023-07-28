package com.homework.spring.mapper;

import com.homework.spring.dto.Book;
import com.homework.spring.entity.Author;
import com.homework.spring.entity.Genre;
import com.homework.spring.repository.AuthorRepository;
import com.homework.spring.repository.GenreRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookMapper {

    private final ModelMapper modelMapper;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookMapper(GenreRepository genreRepository, AuthorRepository authorRepository) {
        this.modelMapper = getModelMapper();
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    public Book toDto(com.homework.spring.entity.Book book) {
        Book bookDto = new Book();
        bookDto.setAuthors(book.getAuthors().stream().map(author -> author.getName() + " " + author.getFatherName()
                + " " + author.getSurname()).collect(Collectors.toList()));
        modelMapper.map(book, bookDto);
        return bookDto;
    }

    public com.homework.spring.entity.Book toEntity(Book book) {
        com.homework.spring.entity.Book bookEntity = new com.homework.spring.entity.Book();
        modelMapper.map(book, bookEntity);
        bookEntity.setAuthors(book.getAuthors().stream().map(author -> {

            String[] authorData = author.split(" ");
            Author author1 = authorRepository.findAuthorByNameAndSurnameAndFatherName(authorData[0],
                    authorData[2], authorData[1]);
            return author1 == null ? new Author(null, authorData[0],
                    authorData[2], authorData[1], authorData.length >= 4 ? authorData[3] : null, null) : author1;
        }).collect(Collectors.toList()));


        Genre genre = genreRepository.findGenreByName(book.getGenre());
        bookEntity.setGenre(genre == null ? new Genre(book.getGenre()) : genre);
        return bookEntity;
    }

    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<com.homework.spring.entity.Book, Book>() {
            @Override
            protected void configure() {
                skip().setAuthors(null);
                map().setGenre(source.getGenre().getName());
            }
        });

        modelMapper.addMappings(new PropertyMap<Book, com.homework.spring.entity.Book>() {
            @Override
            protected void configure() {
                skip().setAuthors(null);
                skip().setGenre(null);
            }
        });
        return modelMapper;
    }
}
