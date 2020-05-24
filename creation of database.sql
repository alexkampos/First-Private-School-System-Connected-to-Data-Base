create database private_school;
use private_school;

CREATE TABLE students(
stud_id int not null auto_increment,
first_name varchar(30) not null,
last_name varchar(30) not null,
date_of_birth date not null,
tuition_fees decimal(7,2) null,
primary key (stud_id)
);


create table courses(
course_id int not null auto_increment,
title varchar(400) not null,
stream varchar(120) not null,
type varchar(100) not null,
start_date date not null,
end_date date not null,
primary key(course_id)
);


create table assignments(
assignment_id int not null auto_increment,
title varchar(300) not null,
description text not null,
sub_date_time datetime not null,
oral_mark decimal (5,2) not null default 0.0,
total_mark decimal (5,2) not null default 0.0,
primary key (assignment_id),
unique (title)
);


alter table courses
 add unique (title);


create table trainers(
trainer_id int not null auto_increment,
first_name varchar(30) not null,
last_name varchar(30) not null,
subject varchar (400),
primary key (trainer_id)
);


create table trainer_course(
tc_id int not null auto_increment,
course_id int not null,
trainer_id int not null,
primary key (tc_id),
unique (course_id,trainer_id),
foreign key (course_id) references courses (course_id),
foreign key (trainer_id) references trainers (trainer_id)
);


create table student_course(
sc_id int not null auto_increment,
stud_id int not null,
course_id int not null,
primary key (sc_id),
unique (stud_id,course_id),
foreign key (stud_id) references students (stud_id),
foreign key (course_id) references courses (course_id)
);


alter table assignments 
add column course_id int not null,
add foreign key (course_id) references courses (course_id);


insert into students (first_name,last_name,date_of_birth,tuition_fees)
values ('Giorgos', 'Tzanis', '1990-03-21', 2000.0),
	   ('Giannis', 'Papadopoulos', '1991-08-12', 1500.0),
       ('Nikos', 'Kerkos', '1988-01-10', 2500.0),
       ('Makis', 'Velios', '1992-11-03', 2000.0),
       ('Marios', 'Kostoglou', '1982-10-13', 2000.0);


insert into trainers (first_name, last_name, subject)
values ('Kostas','Gionis','Programming algebra'),
	   ('Fotis','Fotoglou','SQL'),
       ('Dinos','Boubouras','RDBMS'),
       ('Nikos','Fotiou','Java basics'),
       ('Maria','Gramenou','Java advanced');


alter table courses
drop index title;


insert into courses (title,stream,type,start_date,end_date)
values ('CB10','Java','full-time','2020-05-05','2020-11-05'),
	   ('CB10','C#','full-time','2020-05-10','2020-11-10'),
       ('CB10','Java','part-time','2020-05-06','2020-11-06'),
       ('CB10','C#','part-time','2020-05-11','2020-11-11');

create table course_assignment(
ca_id int not null auto_increment,
course_id int not null,
assignment_id int not null,
primary key (ca_id),
unique (course_id,assignment_id),
foreign key (course_id) references courses (course_id),
foreign key (assignment_id) references assignments (assignment_id)
);

alter table course_assignment
add column assi_sub_date_time datetime not null;


insert into assignments (title, description)
values ('Private School','Create a database for a private scool.'),
	   ('Eshop', 'Create an interface for an eshop.'),
       ('Application','Form a group of 4 people and collaborate to create an application.'),
       ('Java Basics','Create a program using 80% of the things you have learned so far.'),
       ('C# Basics','Create a program using 80% of the things you have learned so far.');

alter table course_assignment
add column assi_sub_date_time datetime default null;

insert into course_assignment (course_id,assignment_id)
values (5,1),
	   (5,2),
       (5,3),
       (5,4),
       (6,1),
       (6,2),
       (6,3),
       (6,5),
       (7,1),
       (7,4),
       (8,1),
       (8,5);


insert into student_course (stud_id,course_id)
values (1,5),
	   (2,6),
       (3,7),
       (4,8),
       (5,5);

insert into trainer_course (course_id,trainer_id)
values (5,4),
	     (5,5),
       (6,1),
       (7,3),
       (8,2);

alter table students
drop column tuition_fees;

alter table student_course
add column tuition_fees decimal(7,2) ;

create table student_assignment(
	sa_id int not null auto_increment,
    stud_id int not null,
    assignment_id int not null,
    course_id int not null,
    grade double default 0.0,
    primary key(sa_id),
    unique (stud_id,assignment_id,course_id),
    foreign key (stud_id) references students (stud_id),
    foreign key (assignment_id) references assignments (assignment_id),
    foreign key (course_id) references courses(course_id)
);

insert into student_assignment (stud_id,assignment_id,course_id)
	values (1,3,5),
		   (1,4,5),
           (5,1,5),
           (5,2,5),
           (2,2,6),
           (2,3,6),
           (3,1,7),
           (3,4,7),
           (4,1,8),
           (4,5,8);

rename table student_assignment to course_student_assignment;

DELIMITER //

CREATE PROCEDURE InsertAllCoursesAssignmentsToStudent(IN courseId INT,IN studId INT)
BEGIN
	INSERT INTO COURSE_STUDENT_ASSIGNMENT (STUD_ID,ASSIGNMENT_ID,COURSE_ID)
    SELECT S.STUD_ID,A.ASSIGNMENT_ID,C.COURSE_ID
    FROM COURSES C
    JOIN STUDENTS S
    CROSS JOIN ASSIGNMENTS A
    WHERE C.COURSE_ID=courseId AND S.STUD_ID=studId;
END // 

DELIMITER ;

ALTER TABLE COURSE_STUDENT_ASSIGNMENT
CHANGE COLUMN GRADE grade DOUBLE NULL DEFAULT NULL ;

UPDATE COURSE_STUDENT_ASSIGNMENT SET GRADE = NULL WHERE SA_ID = 1;
UPDATE COURSE_STUDENT_ASSIGNMENT SET GRADE = NULL WHERE SA_ID = 2;
UPDATE COURSE_STUDENT_ASSIGNMENT SET GRADE = NULL WHERE SA_ID = 3;
UPDATE COURSE_STUDENT_ASSIGNMENT SET GRADE = NULL WHERE SA_ID = 4;
UPDATE COURSE_STUDENT_ASSIGNMENT SET GRADE = NULL WHERE SA_ID = 5;
UPDATE COURSE_STUDENT_ASSIGNMENT SET GRADE = NULL WHERE SA_ID = 6;
UPDATE COURSE_STUDENT_ASSIGNMENT SET GRADE = NULL WHERE SA_ID = 7;
UPDATE COURSE_STUDENT_ASSIGNMENT SET GRADE = NULL WHERE SA_ID = 8;
UPDATE COURSE_STUDENT_ASSIGNMENT SET GRADE = NULL WHERE SA_ID = 9;
UPDATE COURSE_STUDENT_ASSIGNMENT SET GRADE = NULL WHERE SA_ID = 10;

ALTER TABLE COURSE_STUDENT_ASSIGNMENT 
CHANGE COLUMN cSA_ID csa_id INT NOT NULL AUTO_INCREMENT ;

UPDATE COURSE_ASSIGNMENT SET ASSI_SUB_DATE_TIME = '2020-07-05 16:00' WHERE CA_ID=1;
UPDATE COURSE_ASSIGNMENT SET ASSI_SUB_DATE_TIME = '2020-09-05 16:00' WHERE CA_ID=2;
UPDATE COURSE_ASSIGNMENT SET ASSI_SUB_DATE_TIME = '2020-11-05 16:00' WHERE CA_ID=3;
UPDATE COURSE_ASSIGNMENT SET ASSI_SUB_DATE_TIME = '2020-06-05 16:00' WHERE CA_ID=4;
UPDATE COURSE_ASSIGNMENT SET ASSI_SUB_DATE_TIME = '2020-07-10 16:00' WHERE CA_ID=5;
UPDATE COURSE_ASSIGNMENT SET ASSI_SUB_DATE_TIME = '2020-09-10 16:00' WHERE CA_ID=6;
UPDATE COURSE_ASSIGNMENT SET ASSI_SUB_DATE_TIME = '2020-11-06 16:00' WHERE CA_ID=7;
UPDATE COURSE_ASSIGNMENT SET ASSI_SUB_DATE_TIME = '2020-06-10 16:00' WHERE CA_ID=8;
UPDATE COURSE_ASSIGNMENT SET ASSI_SUB_DATE_TIME = '2020-10-06 16:00' WHERE CA_ID=9;
UPDATE COURSE_ASSIGNMENT SET ASSI_SUB_DATE_TIME = '2020-08-06 16:00' WHERE CA_ID=10;
UPDATE COURSE_ASSIGNMENT SET ASSI_SUB_DATE_TIME = '2020-10-11 16:00' WHERE CA_ID=11;
UPDATE COURSE_ASSIGNMENT SET ASSI_SUB_DATE_TIME = '2020-08-11 16:00' WHERE CA_ID=12;

UPDATE STUDENT_COURSE SET TUITION_FEES = 2000 WHERE SC_ID=1;
UPDATE STUDENT_COURSE SET TUITION_FEES = 2000 WHERE SC_ID=2;
UPDATE STUDENT_COURSE SET TUITION_FEES = 2000 WHERE SC_ID=3;
UPDATE STUDENT_COURSE SET TUITION_FEES = 1500 WHERE SC_ID=4;
UPDATE STUDENT_COURSE SET TUITION_FEES = 1500 WHERE SC_ID=5;

INSERT INTO STUDENT_COURSE (STUD_ID,COURSE_ID,TUITION_FEES)
VALUES(3,8,1500),
	  (4,7,1500)