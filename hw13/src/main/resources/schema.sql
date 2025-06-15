create table authors (
    id bigserial,
    full_name varchar(255),
    primary key (id)
);
create table books (
    id bigserial,
    title varchar(255),
    author_id bigint references authors (id) on delete cascade,
    primary key (id)
);
create table comments (
    id bigserial,
    book_id bigint references books (id) on delete cascade,
    data varchar(255),
    primary key (id)
);

create table genres (
    id bigserial,
    name varchar(255),
    primary key (id)
);



create table books_genres (
    book_id bigint references books(id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (book_id, genre_id)
);

create table users (
    id bigserial,
    name varchar(255),
    password varchar(255),
    role varchar(255),
    primary key (id)
);

CREATE TABLE acl_sid (
  id bigint NOT NULL AUTO_INCREMENT,
  principal int NOT NULL,
  sid varchar(100) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS acl_class (
  id bigint NOT NULL AUTO_INCREMENT,
  class varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS acl_entry (
  id bigint NOT NULL AUTO_INCREMENT,
  acl_object_identity varchar(255) NOT NULL,
  ace_order int NOT NULL,
  sid bigint NOT NULL,
  mask int NOT NULL,
  granting int NOT NULL,
  audit_success int NOT NULL,
  audit_failure int NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS acl_object_identity (
  id bigint NOT NULL AUTO_INCREMENT,
  object_id_class bigint NOT NULL,
  object_id_identity varchar(255) NOT NULL,
  parent_object bigint DEFAULT NULL,
  owner_sid bigint DEFAULT NULL,
  entries_inheriting int NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE acl_entry
ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);




ALTER TABLE acl_object_identity
ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);
