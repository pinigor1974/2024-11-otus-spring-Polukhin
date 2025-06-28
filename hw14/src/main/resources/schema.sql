DROP TABLE IF EXISTS BOOKS CASCADE;
DROP TABLE IF EXISTS GENRES CASCADE;
DROP TABLE IF EXISTS AUTHORS CASCADE;
DROP TABLE IF EXISTS COMMENTS CASCADE;
DROP TABLE IF EXISTS AUTHORS_BOOKS cascade;
DROP TABLE IF EXISTS GENRES_BOOKS cascade;


CREATE TABLE AUTHORS(
id bigserial primary key,
name varchar(255));

CREATE TABLE GENRES(
id bigserial primary key,
name varchar(255));

CREATE TABLE BOOKS(
id bigserial primary key,
title varchar(255),
author_id bigint references authors(id),
genre_id bigint references genres(id)
);
CREATE TABLE AUTHORS_BOOKS(
author_id bigint references authors(id),
book_id bigint references books(id));

CREATE TABLE GENRES_BOOKS(
genre_id bigint references genres(id),
book_id bigint references books(id));

CREATE TABLE COMMENTS(
id bigserial primary key,
book_id bigint references books(id) on delete cascade,
title varchar(255)
);
