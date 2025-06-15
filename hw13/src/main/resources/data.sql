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


 INSERT INTO acl_class (id, class) VALUES
   (1, 'ru.otus.hw.dto.BookDto');

 INSERT INTO acl_sid (id, principal, sid) VALUES
 (1, 1, 'user');


 INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
 (1, 1, '1', NULL, 1, 0),
 (2, 1, '2', NULL, 1, 0),
 (3, 1, '3', NULL, 1, 0);


INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure) VALUES
(1, '1', 1, 1, 1, 1, 1, 1),
(2, '2', 1, 1, 1, 1, 1, 1),
(3, '3', 1, 1, 1, 1, 1, 1);


