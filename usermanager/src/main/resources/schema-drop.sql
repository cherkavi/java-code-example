---------------- DROP/REMOVE ------------------------
drop sequence seq_users;
-- GROUP
alter table users_group drop constraint UK_users_group_name;
drop table users_group;

alter table users_user_groups drop constraint UK_users_user_groups;
drop table users_user_groups;

-- PROCESS
alter table users_user_processes drop constraint UK_users_user_processes;
drop table users_user_processes;

alter table users_process drop constraint UK_users_process_name;
drop table users_process;

-- USER
drop table users_user;
alter table users_user drop constraint UK_users_user_login;

