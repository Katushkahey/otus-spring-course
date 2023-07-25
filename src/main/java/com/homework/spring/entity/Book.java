package com.homework.spring.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@Table(name = "book")
@NamedEntityGraph(name = "book_genre_graph", attributeNodes = {@NamedAttributeNode("genre")})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "number_of_pages")
    private int numberOfPages;
    @Column(name = "year_of_publishing")
    private int yearOfPublishing;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "book_author",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "genre_id")
    private Genre genre;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<BookComment> bookComment;

}
