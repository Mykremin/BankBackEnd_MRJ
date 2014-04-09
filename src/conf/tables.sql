drop table Transfer;
drop table CheckingAccount;
drop table Account;
drop table Person;
drop table Postal;

drop sequence transfer_sequence restrict;

----------------------------------------------------------
create sequence transfer_sequence as int start with 100 increment by 1;

create table Postal (
code varchar(4) primary key,
district varchar(50) not null
);

create table Person(
cpr varchar(11) primary key,
title varchar(4) not null,
firstName varchar(40) not null,
lastName varchar(40) not null,
street varchar(100) not null,
code varchar(4) references Postal(code) not null, 
phone varchar(8) not null,
email varchar(50) not null,
password varchar(50) not null
);

create table Account(
cpr varchar(11) references Person(cpr),
dtype varchar(32), 
accountNumber varchar(9) primary key,
balance float(20) not null,
interest float(10) not null
);

create table CheckingAccount(
accountNumber varchar(9) primary key references Account(accountNumber) 
);

create table Transfer(
id int primary key,
amount float(20) not null,
source varchar(9) references Account(accountNumber),
target varchar(9) references Account(accountNumber),
date date
);
