package com.homework.spring.shell;

import com.homework.spring.dto.Book;
import com.homework.spring.entity.BookComment;
import com.homework.spring.entity.Genre;
import com.homework.spring.service.AuthorService;
import com.homework.spring.service.BookCommentService;
import com.homework.spring.service.BookService;
import com.homework.spring.service.GenreService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class ShellCommands {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final BookCommentService bookCommentService;

    public ShellCommands(BookService bookService, GenreService genreService, AuthorService authorService,
                         BookCommentService bookCommentService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.bookCommentService = bookCommentService;
    }

    // addBook "new title" 117 2023 "Ekaterina" "Ivanova" "Igorevna" "1996-02-23" "new genre"
    @ShellMethod(value = "addBook", key = "addBook")
    public void addBook(String title, int numberOfPages, int yearOfPublishing, String authorName, String authorSurname,
                        String authorFatherName, String authorDateOfBirth, String genreName) {

        String author = authorName + " " + authorFatherName + " " + authorSurname + " " + authorDateOfBirth;
        Book book = new Book(null, title, numberOfPages, yearOfPublishing, List.of(author), genreName);
        bookService.save(book);
    }

    @ShellMethod(value = "getBookById", key = "getBookById")
    public com.homework.spring.dto.Book getBookById(Long id) {
        return bookService.findById(id);
    }

    @ShellMethod(value = "getAllBooks", key = "getAllBooks")
    public List<com.homework.spring.dto.Book> getAllBooks() {
        return bookService.findAll();
    }

    @ShellMethod(value = "deleteBookById", key = "deleteBookById")
    public void deleteBookById(Long id) {
        bookService.deleteById(id);
    }

    @ShellMethod(value = "getGenreById", key = "getGenreById")
    public Genre getGenreById(Long id) {
        return genreService.findById(id);
    }

    @ShellMethod(value = "getAllGenres", key = "getAllGenres")
    public List<Genre> getAllGenres() {
        return genreService.findAll();
    }

    @ShellMethod(value = "deleteGenreById", key = "deleteGenreById")
    public void deleteGenreById(Long id) {
        genreService.deleteById(id);
    }

    @ShellMethod(value = "getAuthorById", key = "getAuthorById")
    public com.homework.spring.dto.Author getAuthorById(Long id) {
        return authorService.findById(id);
    }

    @ShellMethod(value = "getAllAuthors", key = "getAllAuthors")
    public List<com.homework.spring.dto.Author> getAllAuthors() {
        return authorService.findAll();
    }

    @ShellMethod(value = "deleteAuthorById", key = "deleteAuthorById")
    public void deleteAuthorById(Long id) {
        authorService.deleteById(id);
    }

    @ShellMethod(value = "getBookCommentsByBookId", key = "getBookCommentsByBookId")
    public List<BookComment> getBookCommentsByBookId(Long bookId) {
        return bookCommentService.findByBookId(bookId);
    }

    @ShellMethod(value = "deleteBookCommentById", key = "deleteBookCommentById")
    public void deleteBookCommentById(Long id) {
        bookCommentService.deleteById(id);
    }
}
