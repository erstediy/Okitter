insert into user(id,username,password,active)
values(1,'admin','admin',true);

insert into user_role (user_id, roles)
values(1,'ADMIN'),(1,'USER');