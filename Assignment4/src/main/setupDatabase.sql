drop database if exists assignment4;
create database assignment4;
use assignment4;

create table user (
	userId BIGINT unique Primary Key AUTO_INCREMENT,
	userName varchar(255) unique,
	email  varchar(255),
	encryptedPassword varchar(255)

);

create table role (
  	roleId   BIGINT NOT NULL Primary Key AUTO_INCREMENT,
	roleName VARCHAR(30) NOT NULL UNIQUE

) ;


create table user_role (
  	ID BIGINT NOT NULL Primary Key AUTO_INCREMENT,
 	userId  BIGINT NOT NULL,
  	roleId BIGINT NOT NULL
);


alter table user_role
  add constraint USER_ROLE_UK unique (userId, roleId);


alter table user_role
  add constraint USER_ROLE_FK1 foreign key (userId)
  references user (userId);

 
alter table user_role
  add constraint USER_ROLE_FK2 foreign key (roleId)
  references role (roleId);


create table phone (

	phoneId INTEGER AUTO_INCREMENT UNIQUE NOT NULL,
	manufacturer varchar(255),
	model varchar(255),
	price DOUBLE,
	screenSize varchar(255),
	battery INTEGER,
	ram varchar(255),
	storage INTEGER,
	processor varchar(255),
	dimensions varchar(255),
	waterProofRating varchar(255)
)
 	
	








