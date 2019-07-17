---------------- CREATE ------------------------
-- USER
create table users_user (
	id integer not null,
	created_date timestamp,
	login varchar(255),
	name varchar(255),
	password varchar(255),
	surname varchar(255),
	primary key (id)
);
alter table users_user
add constraint UK_users_user_login unique (login);


-- GROUP
create table users_group (
	id integer not null,
	created_date timestamp,
	name varchar(255),
	primary key (id)
);

alter table users_group
add constraint UK_users_group_name unique (name);

create table users_user_groups (
	user_id integer not null,
	groups_id integer not null,
	primary key (user_id, groups_id)
);

alter table users_user_groups
add constraint UK_users_user_groups unique (user_id, groups_id);

-- PROCESS
create table users_process (
	id integer not null,
	created_date timestamp,
	name varchar(255),
	primary key (id)
);
alter table users_process
add constraint UK_users_process_name unique (name);

create table users_user_processes (
	user_id integer not null,
	processes_id integer not null,
	primary key (user_id, processes_id)
);

alter table users_user_processes
add constraint UK_users_user_processes unique (user_id, processes_id);

create sequence seq_users;
