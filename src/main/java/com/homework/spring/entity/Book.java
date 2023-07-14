package com.homework.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    Long id;
    String title;
    int numberOfPages;
    int yearOfPublishing;
    Author author;
    Genre genre;

    public Book(Long id, String newTitle, int newNumberOfPages, int newYearOfPublishing) {
        this.id = id;
        this.title = newTitle;
        this.numberOfPages = newNumberOfPages;
        this.yearOfPublishing = newYearOfPublishing;
    }
}
