package com.homework.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Author {
    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "father_name")
    private String fatherName;
    @Column(name = "date_of_birth")
    private String dateOfBirth;

    public Author(String name, String surname, String fatherName, String dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.fatherName = fatherName;
        this.dateOfBirth = dateOfBirth;
    }

}
