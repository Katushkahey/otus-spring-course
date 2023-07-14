package com.homework.spring.dto;

import lombok.Data;

@Data
public class Book {

    Long id;
    String title;
    int numberOfPages;
    int yearOfPublishing;
    String authorName;
    String authorSurname;
    String authorFatherName;
    String authorDateOfBirth;
    String genre;
}
