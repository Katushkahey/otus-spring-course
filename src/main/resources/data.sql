insert into AUTHOR (id, name, surname, father_name, date_of_birth) values (1, 'Alexander', 'Pushkin', 'Sergeevich', '1799-05-26');
insert into AUTHOR (id, name, surname, father_name, date_of_birth) values (2, 'Vlodimir', 'Mayakovskiy', 'Vlodimirovich', '1893-07-07');
insert into AUTHOR (id, name, surname, father_name, date_of_birth) values (3, 'Lew', 'Tolstoi', 'Nikolaevich', '1828-08-28');
insert into AUTHOR (id, name, surname, father_name, date_of_birth) values (4, 'Boris', 'Ziv', 'Germanovich', '1928-02-25');
insert into AUTHOR (id, name, surname, father_name, date_of_birth) values (5, 'Vladimir', 'Goldich', 'Anatolevich', '1952-04-02');

insert into GENRE (id, name) values (1, 'poem');
insert into GENRE (id, name) values (2, 'poetry');
insert into GENRE (id, name) values (3, 'roman');
insert into GENRE (id, name) values (4, 'ychebnik');

insert into BOOK (id, title, number_of_pages, year_of_publishing, genre_id) values (1, 'Mednyi vsadnik', 14, 1833, 1);
insert into BOOK (id, title, number_of_pages, year_of_publishing, genre_id) values (2, 'Poslyshaite', 1, 1914, 2);
insert into BOOK (id, title, number_of_pages, year_of_publishing, genre_id) values (3, 'Anna Karenina', 478, 1833, 3);
insert into BOOK (id, title, number_of_pages, year_of_publishing, genre_id) values (4, 'Evgenii Onegin', 230, 1832, 3);
insert into BOOK (id, title, number_of_pages, year_of_publishing, genre_id) values (5, 'Horoshee otnoshenie k loshadyam', 1, 1918, 2);
insert into BOOK (id, title, number_of_pages, year_of_publishing, genre_id) values (6, 'Voina i mir', 1274, 1869, 3);
insert into BOOK (id, title, number_of_pages, year_of_publishing, genre_id) values (7, 'Algebra 7 klass', 136, 2005, 4);

insert into BOOK_AUTHOR(id, book_id, author_id) values (1, 1, 1);
insert into BOOK_AUTHOR(id, book_id, author_id) values (2, 2, 2);
insert into BOOK_AUTHOR(id, book_id, author_id) values (3, 3, 3);
insert into BOOK_AUTHOR(id, book_id, author_id) values (4, 4, 1);
insert into BOOK_AUTHOR(id, book_id, author_id) values (5, 5, 2);
insert into BOOK_AUTHOR(id, book_id, author_id) values (6, 6, 3);
insert into BOOK_AUTHOR(id, book_id, author_id) values (7, 7, 4);
insert into BOOK_AUTHOR(id, book_id, author_id) values (8, 7, 5);

insert into BOOK_COMMENT(id, title, text_, book) values (1, 'Cool book', 'Syper ychebnik; ja cdal EGE na 100 ballov', 7);
insert into BOOK_COMMENT(id, title, text_, book) values (2, 'Nenavizhy', 'odno muchenie; pochemy tak slozgno???', 7)