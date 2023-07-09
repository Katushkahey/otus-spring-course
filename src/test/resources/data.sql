insert into AUTHOR (id, name, surname, father_name, date_of_birth) values (1, 'Alexander', 'Pushkin', 'Sergeevich', '1799-05-26');

insert into GENRE (id, name) values (1, 'poem');
insert into BOOK (id, title, number_of_pages, year_of_publishing, author_id, genre_id) values (1, 'Mednyi vsadnik', 14, 1833, 1, 1);

insert into AUTHOR (id, name, surname, father_name, date_of_birth) values (2, 'Vlodimir', 'Mayakovskiy', 'Vlodimirovich', '1893-07-07');

insert into GENRE (id, name) values (2, 'poetry');
insert into BOOK (id, title, number_of_pages, year_of_publishing, author_id, genre_id) values (2, 'Poslyshaite', 1, 1914, 2, 2);

insert into AUTHOR (id, name, surname, father_name, date_of_birth) values (3, 'Lew', 'Tolstoi', 'Nikolaevich', '1828-08-28');
insert into GENRE (id, name) values (3, 'roman');
insert into BOOK (id, title, number_of_pages, year_of_publishing, author_id, genre_id) values (3, 'Anna Karenina', 478, 1833, 3, 3);