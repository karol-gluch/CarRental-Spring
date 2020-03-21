insert into user (id,username,password) values (1,'admin123','$2a$10$g09DwEO7fAOPUWJfJRekIuJov8m1tIWbC7HODMMJK9d8qNarsgdCy');
insert into user (id,username,password) values (2,'user123','$2a$10$BOt39Rnda0f0lSGPzR7y7.VOvVXC6Tyt.TFPXtVpKuxdB2G1BDqtS');
insert into role (id,name) values (1,'ROLE_ADMIN');
insert into role (id,name) values (2,'ROLE_USER');
insert into user_roles (users_id,roles_id) values (1,1);
insert into user_roles (users_id,roles_id) values (2,2);