delete from user_role;
delete from usr;

insert into usr (id,active,password,username)
           values(1,true,'$2y$08$uD3MCTsO41x.D4m.Kiwj5ei9E0pgZQsaJSIGwHs/veDXYaIXx5utG','user'),
                 (2,true,'$2y$08$uD3MCTsO41x.D4m.Kiwj5ei9E0pgZQsaJSIGwHs/veDXYaIXx5utG','user2');


insert into user_role (user_id,roles)
               values(1,'USER'),(1,'ADMIN'),(2,'USER');