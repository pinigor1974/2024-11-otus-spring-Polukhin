insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3'),
       ('Genre_4'), ('Genre_5'), ('Genre_6');

insert into books(title, author_id)
values ('BookTitle_1', 1), ('BookTitle_2', 2), ('BookTitle_3', 3);

insert into books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 4),
       (3, 5),   (3, 6);
insert into comments(book_id, data)
values (1, 'Comment_1'),   (1, 'Comment_2'),
       (2, 'Comment_3'),   (2, 'Comment_4'),
       (3, 'Comment_5'),   (3, 'Comment_6');

 insert into users(name, password, role)
 values('user', '$2a$10$Feteh91U9skzPBB9P8HYkuvH4BrFlJfDy/xk0Ps.rizCoahFcT4Oe', 'ROLE_USER');