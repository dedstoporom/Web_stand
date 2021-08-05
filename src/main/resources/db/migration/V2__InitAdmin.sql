insert into db_service.usr (id,username,password,active)
values(1,'admin','$2y$08$OzGyxskP53MR7iJwkrFi1usv9xDqS0x/PcfM2.I4cao/fpJtuLLTO',true);
insert into db_service.user_role (user_id,roles)
values(1,'USER'),(1,'ADMIN');
