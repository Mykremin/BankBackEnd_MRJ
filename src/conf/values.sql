delete from Transfer;
delete from CheckingAccount;
delete from Account;
delete from Person;
delete from Postal;

----------------------------------------------------

insert into POSTAL(CODE,DISTRICT) values
('2635', 'Ishøj'),
('2660', 'Brøndby Strand'),
('2800', 'Kgs. Lyngby');

insert into PERSON(CPR,TITLE,FIRSTNAME,LASTNAME,STREET,CODE,PHONE,EMAIL,PASSWORD) values
('040493-2543', 'Mr.', 'Mykremin', 'Asanovski', 'Kærlunden 25', '2660', '31411705', 'Mykremin_93@hotmail.com', '12345678'),
('090690-3159', 'Mr.', 'Kurt', 'Hansen', 'Trongårdsvej 15', '2800', '30899308', 'Kurt@gmail.dk', '12345687'),
('030287-3114', 'Mrs.', 'Sonja', 'Andersen', 'Vejlebrovej 24C', '2635', '24120618', 'Sonja@gmail.dk', '12341234');

insert into ACCOUNT(CPR,DTYPE,ACCOUNTNUMBER,BALANCE,INTEREST) values
('040493-2543', 'Checking account', '7860-1002', 1000, 0.5),
('090690-3159', 'Checking account', '7860-1003', 1500, 0.5),
('030287-3114', 'Checking account', '7860-' || char(next value for account_sequence) , 2000, 0.5);

insert into CHECKINGACCOUNT(ACCOUNTNUMBER) values
('7860-1002'),
('7860-1003');

insert into TRANSFER(ID,AMOUNT,"SOURCE",TARGET,"DATE") values
('100', 300, '7860-1002', '7860-1003', '2014-11-20'),
('101', 300, '7860-1003', '7860-1002', '2014-04-04');





