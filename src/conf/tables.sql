drop table Transfer;
drop table CheckingAccount;
drop table Account;
drop table Person;
drop table Postal;

drop sequence account_sequence restrict;
drop sequence transfer_sequence restrict;


----------------------------------------------------------
create sequence transfer_sequence as int start with 100 increment by 1;
create sequence account_sequence as int start with 10001000 increment by 1;

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
accountNumber varchar(40) primary key,
balance decimal(20,2) not null,
interest decimal(4,2) not null
);

create table CheckingAccount(
accountNumber varchar(40) primary key references Account(accountNumber) 
);

create table Transfer(
id varchar(20) primary key,
amount decimal(20,2) not null,
source varchar(40) references Account(accountNumber),
target varchar(40) references Account(accountNumber),
date date
);

-- create table owned_accounts(
-- cpr varchar referemces person(cpr),
-- accountnumber varchar references accont(accountnumber)
-- slet cpr fra account
