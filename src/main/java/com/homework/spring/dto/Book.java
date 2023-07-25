package com.homework.spring.dto;

import com.homework.spring.entity.BookComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Book {

    Long id;
    String title;
    int numberOfPages;
    int yearOfPublishing;
    List<String> authors;
    String genre;

}


