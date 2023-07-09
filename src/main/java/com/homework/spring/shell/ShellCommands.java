package com.homework.spring.shell;

import com.homework.spring.entity.Author;
import com.homework.spring.entity.Book;
import com.homework.spring.entity.Genre;
import com.homework.spring.service.AuthorService;
import com.homework.spring.service.BookService;
import com.homework.spring.service.GenreService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
public class ShellCommands {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    public ShellCommands(BookService bookService, GenreService genreService, AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    // addBook "new title" 117 2023 "Ekaterina" "Ivanova" "Igorevna" "1996-02-23" "new genre"
    @ShellMethod(value = "addBook", key = "addBook")
    public void addBook(String title, int numberOfPages, int yearOfPublishing, String authorName, String authorSurname,
                        String authorFatherName, String authorDateOfBirth, String genreName) {

        Author author = new Author(null, authorName, authorSurname, authorFatherName, authorDateOfBirth);
        Genre genre = new Genre(null, genreName);
        Book book = new Book(null, title, numberOfPages, yearOfPublishing, author, genre);
        bookService.add(book);
    }

    // updateBook
//    @ShellMethod(value = "updateBook", key = "addBook")
//    public void updateBook(Long id, String newTitle, int newNumberOfPages, int newYearOfPublishing, String newAuthorName,
//                           String newAuthorSurname, String newAuthorFatherName, String newAuthorDateOfBirth,
//                           String newGenreName) {
//
//        Author author = new Author(null, authorName, authorSurname, authorFatherName, authorDateOfBirth);
//        Genre genre = new Genre(null, genreName);
//        Book book = new Book(id, title, numberOfPages, yearOfPublishing, author, genre);
//        bookService.update(book);
//    }

    @ShellMethod(value = "getBookById", key = "getBookById")
    public Book getBookById(Long id) {
        return bookService.getById(id);
    }

    @ShellMethod(value = "getAllBooks", key = "getAllBooks")
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @ShellMethod(value = "getGenreById", key = "getGenreById")
    public Genre getGenreById(Long id) {
        return genreService.getById(id);
    }

    @ShellMethod(value = "getAllGenres", key = "getAllGenres")
    public List<Genre> getAllGenres() {
        return genreService.getAll();
    }

    @ShellMethod(value = "getAuthorById", key = "getAuthorById")
    public Author getAuthorById(Long id) {
        return authorService.getById(id);
    }

    @ShellMethod(value = "getAllAuthors", key = "getAllAuthors")
    public List<Author> getAllAuthors() {
        return authorService.getAll();
    }
}
