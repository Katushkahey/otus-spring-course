package com.homework.spring.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document
@EqualsAndHashCode
public class Book {
    @Id
    private String id;
    @Column(name = "title")
    private String title;
    @Column(name = "number_of_pages")
    private int numberOfPages;
    @Column(name = "year_of_publishing")
    private int yearOfPublishing;

    private List<Author> authors;
    private Genre genre;

    public Book(String title, int numberOfPages, int yearOfPublishing, List<Author> authors, Genre genre) {
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.yearOfPublishing = yearOfPublishing;
        this.authors = authors;
        this.genre = genre;
    }

}
