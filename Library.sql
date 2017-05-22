DROP SCHEMA IF EXISTS Library;
CREATE SCHEMA Library;
USE Library;

DROP TABLE IF EXISTS BOOKS;
CREATE TABLE Books (
	ISBN VARCHAR(13) NOT NULL,
	Title varchar(256) NOT NULL,
	Availability Boolean Default true,
	Authors varchar(256),	
CONSTRAINT pk_ISBN PRIMARY KEY (ISBN)
  	);

LOAD DATA LOCAL INFILE 'D:/Library Done (2)/Library/Library/books.csv'
INTO TABLE Books
Fields TERMINATED BY '\t' 
enclosed by '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(@ignore, ISBN, Title, Authors);

DROP TABLE IF EXISTS BORROWER;
CREATE TABLE BORROWER (
	CARD_ID INT NOT NULL AUTO_INCREMENT,
	Bname varchar(256) NOT NULL,
	SSN CHAR(11) NOT NULL,
Address varchar(256) NOT NULL,
Phone CHAR(14),	
Books_Borrowed INT(1) Default 0,
CONSTRAINT pk_CARD_ID PRIMARY KEY (CARD_ID)
  	);

LOAD DATA LOCAL INFILE 'D:/Library Done (2)/Library/borrowers.csv'
INTO TABLE BORROWER
Fields TERMINATED BY ',' 
enclosed by '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(Card_id, ssn, @first_name, @last_name, @ignore, @address, @city, @state, phone)
SET Address = CONCAT(@address, ' ', @city, ' ', @state), Bname = CONCAT(@first_name, ' ', @last_name);

DROP TABLE IF EXISTS BOOK_LOANS;
CREATE TABLE BOOK_LOANS (

	LOAN_ID INT NOT NULL AUTO_INCREMENT,
	CARD_ID INT NOT NULL,
	ISBN VARCHAR(13) NOT NULL,
	Date_out Date,
	Due_Date Date,
Date_in Date,
CONSTRAINT pk_LOAN_ID PRIMARY KEY (LOAN_ID) ,
CONSTRAINT fk_bookloans_book FOREIGN KEY (ISBN) REFERENCES Book(ISBN),
CONSTRAINT fk_bookloans_borrower FOREIGN KEY (Card_id) REFERENCES Borrower(Card_id)
  	);

DROP TABLE IF EXISTS FINES;
CREATE TABLE FINES (

	LOAN_ID INT NOT NULL,
	Fine_amt Decimal(4,2) Default 0,
	Paid Boolean Default Null,
	CONSTRAINT pk_LOAN_ID PRIMARY KEY (LOAN_ID),
	CONSTRAINT fk_fines_bookloans FOREIGN KEY (Loan_id) REFERENCES Book_Loans(Loan_id)
);
	DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS (
	Author_ID INT NOT NULL AUTO_INCREMENT,
	Name VARCHAR(256),
		CONSTRAINT pk_Author_ID PRIMARY KEY (Author_ID)
		);
------------------Not my code

drop temporary table if exists authors_2;
create temporary table AUTHORS_2(Isbn varchar(10) NOT NULL,author_name varchar(255) NOT NULL, primary key(Isbn));

LOAD DATA LOCAL INFILE 'D:/Library Done (2)/Library/Library/books.csv'
INTO TABLE authors_2
IGNORE 1 rows
(@ignore, ISBN, @ignore,author_name);


drop temporary table if exists authors_3;
create temporary table AUTHORS_3(Isbn varchar(13) NOT NULL,author_name varchar(255) NOT NULL);
insert ignore into authors_3
select
  authors_2.isbn,
  SUBSTRING_INDEX(SUBSTRING_INDEX(authors_2.author_name, ',', numbers.n), ',', -1) author_name
from
  (select distinct 1 n union all
   select distinct 2 union all select 3 union all
   select distinct 4 union all select 5) numbers INNER JOIN authors_2
  on CHAR_LENGTH(authors_2.author_name)
     -CHAR_LENGTH(REPLACE(authors_2.author_name, ',', ''))>=numbers.n-1
order by
  ISBN, n;



drop temporary table if exists authors_4;
create temporary table AUTHORS_4(Isbn varchar(13) NOT NULL,author_name varchar(255) NOT NULL);

insert into authors_4
SELECT isbn,author_name FROM authors_3 GROUP BY isbn,author_name;

alter table authors_4 add author_id int auto_increment primary key;



alter table authors_4 add title varchar(10) default null;

alter table authors_4 
add column fname varchar(15),
add column mname varchar(15) default null,
add column lname varchar(15),
add column suffix varchar(10) default null after title;


drop temporary table if exists authors_5;

CREATE temporary TABLE Authors_5 (
  author_id int(11) NOT NULL AUTO_INCREMENT,
  fullname varchar(1000) NOT NULL,
  title varchar(100) DEFAULT NULL,
  fname varchar(100) DEFAULT NULL,
  mname varchar(100) DEFAULT NULL,
  lname varchar(100) DEFAULT NULL,
  suffix varchar(50) Default null,
  PRIMARY KEY (author_id)
);

insert into authors_5
select
  min(authors_4.author_id), authors_4.author_name, authors_4.title, SUBSTRING_INDEX(SUBSTRING_INDEX(author_name, ' ', 1), ' ', -1) AS fname,
   If(  length(author_name) - length(replace(author_name, ' ', ''))>1,  
       SUBSTRING_INDEX(SUBSTRING_INDEX(author_name, ' ', 2), ' ', -1) ,NULL) 
           as mname,
   SUBSTRING_INDEX(SUBSTRING_INDEX(author_name, ' ', 3), ' ', -1) AS lname, authors_4.suffix
FROM authors_4 group by authors_4.author_name;

drop temporary table if exists book_authors_1;

CREATE temporary TABLE BOOK_AUTHORS_1 (
  Isbn varchar(13) NOT NULL,
  author_id int(11) NOT NULL,
  PRIMARY KEY (`Isbn`,`author_id`));


insert into BOOK_AUTHORS_1
select
  authors_4.isbn, authors_5.author_id
FROM authors_4 join authors_5 on authors_5.fullname = authors_4.author_name;


--
-- Table structure for authors
--
DROP table if exists authors;
CREATE TABLE Authors (
  author_id int(11) NOT NULL AUTO_INCREMENT,
  fullname varchar(1000) NOT NULL,
  title varchar(100) DEFAULT NULL,
  fname varchar(100) DEFAULT NULL,
  mname varchar(100) DEFAULT NULL,
  lname varchar(100) DEFAULT NULL,
  suffix varchar(50) Default null,
  PRIMARY KEY (author_id)
) ENGINE=InnoDB auto_increment=000001 DEFAULT CHARSET=utf8;

--
-- Loading data into authors
--
insert into authors
select * from authors_5;


--
-- Table structure for Book_Authors
--
DROP table if exists book_authors;
CREATE TABLE BOOK_AUTHORS (
  Isbn varchar(13) NOT NULL,
  author_id int(11) NOT NULL,
  PRIMARY KEY (`Isbn`,`author_id`),
 -- CONSTRAINT `book_authors_fk1` FOREIGN KEY (`Isbn`) REFERENCES `BOOK` (`Isbn`),
  CONSTRAINT `book_authors_fk2` FOREIGN KEY (`author_id`) REFERENCES `authors` (`author_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Loading data into book_authors

insert into book_authors
select * from book_authors_1;
