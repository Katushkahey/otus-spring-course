package com.homework.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@ToString
@Table(name = "book_comment")
@AllArgsConstructor
@NoArgsConstructor
public class BookComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "text_")
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book")
    @ToString.Exclude
    private Book book;

}
