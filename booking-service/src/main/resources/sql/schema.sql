drop table if exists t_user;
create table t_user
(
id int unsigned not null auto_increment comment 'PK',
name varchar(100) not null comment 'username',
password varchar(256) not null comment 'encrypted password',
primary key (id),
unique index idx_name (name)
);

drop table if exists t_car;
create table t_car
(
id int unsigned not null auto_increment comment 'PK',
car_model varchar(100) not null comment 'car model',
price int unsigned not null comment 'renting price per day in RMB cents',
number int unsigned not null comment 'number of cars',
info_updated_at TIMESTAMP not null comment 'update time of car info' default now(),
inventory_updated_at TIMESTAMP not null comment 'update time of inventory' default now(),
primary key (id),
unique index idx_car_model (car_model)
);

drop table if exists t_inventory;
create table t_inventory
(
id int unsigned not null auto_increment comment 'PK',
car_model varchar(100) not null comment 'car model',
price int unsigned not null comment 'renting price at the date, in RMB cents',
num_in_stock int unsigned not null comment 'number in stock',
date TIMESTAMP not null comment 'date',
updated_at TIMESTAMP not null comment 'update time' default now(),
primary key (id),
index idx_date (date, car_model)
);

drop table if exists t_order;
create table t_order
(
id int unsigned not null auto_increment comment 'PK',
user_id int unsigned not null comment 'the user who placed the order',
car_model varchar(100) not null comment 'car model',
start_date TIMESTAMP not null comment 'start date',
end_date TIMESTAMP not null comment 'end date',
status int unsigned not null comment 'status of order, 50 - canceled, 100 - new, 120 - paid, 200 - completed',
amount int unsigned not null comment 'amount of the order, in RMB cents',
created_at TIMESTAMP not null comment 'create time' default now(),
updated_at TIMESTAMP not null comment 'update time' default now(),

primary key (id),
index idx_user_id (user_id),
index idx_date (start_date, end_date)

);