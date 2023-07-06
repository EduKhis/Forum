CREATE TABLE message
(
    id          bigint NOT NULL AUTO_INCREMENT,
    date        datetime(6),
    description varchar(255),
    filename    varchar(255),
    teg         varchar(255),
    user_id     bigint,
    primary key (id)

);
CREATE TABLE message_seq (next_val bigint) engine=InnoDB;
INSERT INTO message_seq VALUES (3);
CREATE TABLE user
(
    id              bigint NOT NULL AUTO_INCREMENT,
    activation_code varchar(255),
    active          bit    not null,
    email           varchar(255),
    password        varchar(255),
    username        varchar(255),
    primary key (id)
);
CREATE TABLE user_list
(
    user_id bigint NOT NULL,
    list    varchar(255)
);
CREATE TABLE users_seq (next_val bigint) engine=InnoDB;
INSERT INTO users_seq VALUES (2);
ALTER TABLE
    message
    add
        constraint FKb3y6etti1cfougkdr0qiiemgv foreign key (user_id) references user (id);
ALTER TABLE
    user_list
    add
        constraint FKl55t4lsmeal3xv7ok4s1xi32f foreign key (user_id) references user (id);
