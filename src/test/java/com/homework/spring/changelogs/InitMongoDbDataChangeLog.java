package com.homework.spring.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.homework.spring.entity.Author;
import com.homework.spring.entity.Book;
import com.homework.spring.entity.BookComment;
import com.homework.spring.entity.Genre;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@ChangeLog
public class InitMongoDbDataChangeLog {

    private Author pushkinAuthor;
    private Author mayakovskiyAuthor;
    private Author tolstoiAuthor;
    private Author zivAuthor;
    private Author goldichAuthor;
    private Genre poem;
    private Genre poetry;
    private Genre roman;
    private Genre ychebnik;
    private Book ychebnikBook;

    @ChangeSet(order = "000", id = "dropDB", author = "ekivanova", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "ekivanova", runAlways = true)
    public void initAuthors(MongoTemplate template) {

        pushkinAuthor = template.save(new Author("Alexander", "Pushkin", "Sergeevich", "1799-05-26"));
        mayakovskiyAuthor = template.save(new Author("Vlodimir", "Mayakovskiy", "Vlodimirovich", "1893-07-07"));
        tolstoiAuthor = template.save(new Author("Lew", "Tolstoi", "Nikolaevich", "1828-08-28"));
        zivAuthor = template.save(new Author("Boris", "Ziv", "Germanovich", "1928-02-25"));
        goldichAuthor = template.save(new Author("Vladimir", "Goldich", "Anatolevich", "1952-04-02"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "ekivanova", runAlways = true)
    public void initGenres(MongoTemplate template) {
        poem = template.save(new Genre("poem"));
        poetry = template.save(new Genre("poetry"));
        roman = template.save(new Genre("roman"));
        ychebnik = template.save(new Genre("ychebnik"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "ekivanova", runAlways = true)
    public void initBooks(MongoTemplate template) {

        template.save(new Book("Mednyi vsadnik", 14, 1833,
                List.of(pushkinAuthor), poem));

        template.save(new Book("Poslyshaite", 1, 1914,
                List.of(mayakovskiyAuthor), poetry));

        template.save(new Book("Anna Karenina", 478, 1833,
                List.of(tolstoiAuthor), roman));

        template.save(new Book("Evgenii Onegin", 230, 1832,
                List.of(pushkinAuthor), poem));

        template.save(new Book("Horoshee otnoshenie k loshadyam", 1, 1918,
                List.of(mayakovskiyAuthor), poetry));

        template.save(new Book("Voina i mir", 1274, 1869,
                List.of(tolstoiAuthor), roman));

        ychebnikBook = template.save(new Book("Algebra 7 klass", 136, 2005,
                List.of(zivAuthor, goldichAuthor), ychebnik));

    }

    @ChangeSet(order = "004", id = "initComments", author = "ekivanova", runAlways = true)
    public void initComments(MongoTemplate template) {
        template.save(new BookComment(null, "Cool book", "Syper ychebnik; ja cdal EGE na 100 ballov", ychebnikBook));
        template.save(new BookComment(null, "Nenavizhy", "odno muchenie; pochemy tak slozgno???", ychebnikBook));
    }
}
