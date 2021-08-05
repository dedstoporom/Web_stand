create table message_likes
(
    user_id BIGINT not null references usr,
    message_id BIGINT not null references message,
    primary key(user_id,message_id)
)