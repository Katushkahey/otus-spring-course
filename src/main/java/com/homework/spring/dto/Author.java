package com.homework.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    String id;
    String name;
    String surname;
    String fatherName;
    String dateOfBirth;
    List<String> books;
}
