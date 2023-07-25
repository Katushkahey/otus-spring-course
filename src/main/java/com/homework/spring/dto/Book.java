package com.homework.spring.dto;

import com.homework.spring.entity.BookComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    Long id;
    String title;
    int numberOfPages;
    int yearOfPublishing;
    List<String> authors;
    String genre;

}


