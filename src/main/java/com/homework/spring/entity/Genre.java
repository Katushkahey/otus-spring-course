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
public class Genre {
    @Id
    private String id;
    @Column(name = "name")
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
