package com.homework.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Author {

    Long id;
    String name;
    String surname;
    String fatherName;
    String dateOfBirth;
    List<String> books;
}
