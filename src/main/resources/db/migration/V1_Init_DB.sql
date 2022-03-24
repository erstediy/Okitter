create table message
(
    id       integer       not null auto_increment,
    filename varchar(255),
    tag      varchar(255),
    text     varchar(2048) not null,
    user_id  integer,
    primary key (id)
);

create table user
(
    id              integer      not null auto_increment,
    activation_code varchar(255),
    active          bit          not null,
    email           varchar(255),
    password        varchar(255) not null,
    username        varchar(255) not null,
    primary key (id)
);

create table user_role
(
    user_id integer not null,
    roles   varchar(255)
);

alter table if exists message
    add constraint message_user_fk
    foreign key (user_id) references user (id);

alter table if exists user_role
    add constraint user_role_user_fk
    foreign key (user_id) references user (id);
