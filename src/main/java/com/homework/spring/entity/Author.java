package com.homework.spring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "father_name")
    private String fatherName;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<Book> books;

}
