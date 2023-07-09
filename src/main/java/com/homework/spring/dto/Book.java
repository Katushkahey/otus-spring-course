package com.homework.spring.dto;

import com.homework.spring.entity.Author;
import com.homework.spring.entity.Genre;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Book {

    String title;
    int numberOfPages;
    int yearOfPublishing;
    String authorName;
    String authorSurname;
    String authorFatherName;
    String authorDateOfBirth;
    String genre;
}
