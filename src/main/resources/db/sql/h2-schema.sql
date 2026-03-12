create table members
(
    id                 bigint primary key      not null auto_increment,
    username           varchar(50)             not null,
    password           varchar(200)             not null,
    nickname           varchar(50)             not null,
    created_date       timestamp default now() not null,
    last_modified_date timestamp
);

create table contents
(
    id                  bigint primary key      not null auto_increment,
    title               varchar(100)            not null,
    description         text,
    view_count          bigint                  not null,
    created_date        timestamp default now(),
    created_by          varchar(50)             not null,
    last_modified_date  timestamp,
    last_modified_by    varchar(50)
);
create table member_role
(
    id                 bigint primary key       not null auto_increment,
    member_id          bigint                   not null,
    role               varchar(50)              not null,
    constraint fk_member_role_member_id foreign key (member_id) references members (id)
);
