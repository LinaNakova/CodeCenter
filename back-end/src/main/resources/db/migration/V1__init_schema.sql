CREATE TABLE app_users
(
    id            bigserial primary key,
    username      text not null,
    name          text not null,
    surname       text not null,
    email         text not null,
    app_user_role text default 'USER'
);

CREATE TABLE activation_tokens
(
    token       text primary key,
    app_user_id bigint not null,
    constraint fk_activation_tokens_app_user_id foreign key (app_user_id) references app_users (id)
);

CREATE TABLE question
(
    id                 bigserial primary key,
    title              text   not null,
    question_text      text   not null,
    parent_question_id bigint default null,
    app_user_id        bigint not null,
    constraint fk_question_user_id foreign key (app_user_id) references app_users (id),
    constraint fk_question_parent_question_id foreign key (parent_question_id) references question (id)
);

CREATE TABLE tag
(
    id          bigserial primary key,
    name        text not null,
    description text not null
);

CREATE TABLE question_tag
(
    id          bigserial primary key,
    question_id bigint not null,
    tag_id      bigint not null,
    constraint fk_question_question_id foreign key (question_id) references question (id),
    constraint fk_tag_tag_id foreign key (tag_id) references tag (id)
);
