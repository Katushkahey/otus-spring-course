package com.homework.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Data
@Document
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookComment {
    @Id
    private String id;
    @Column(name = "title")
    private String title;
    @Column(name = "text_")
    private String text;
    private Book book;

}
