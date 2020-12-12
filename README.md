
## Name

Private School System with Database

## Description

~ Java SE Application ~\
A Private School System Simulator with a Database where following are implemented:\
\
&emsp;&#8226;&emsp;A design of application's ERD\
\
&emsp;&#8226;&emsp;A design of application's ERD's schema\
\
&emsp;&#8226;&emsp;SQL query that shows a list of all the students\
\
&emsp;&#8226;&emsp;SQL query that shows a list of all the trainers\
\
&emsp;&#8226;&emsp;SQL query that shows a list of all the assignments\
\
&emsp;&#8226;&emsp;SQL query that shows a list of all the courses\
\
&emsp;&#8226;&emsp;SQL query that shows a list of all the students per course\
\
&emsp;&#8226;&emsp;SQL query that shows a list of all the trainers per course\
\
&emsp;&#8226;&emsp;SQL query that shows a list of all the assignments per course\
\
&emsp;&#8226;&emsp;SQL query that shows a list of all the assignments per course per student\
\
&emsp;&#8226;&emsp;SQL query that shows a list of a list of students that belong to more than one courses\
\
&emsp;&#8226;&emsp;Application options after connecting to database:\
\
&emsp;&emsp;&#8226;&emsp;Prints of above queries\
\
&emsp;&emsp;&#8226;&emsp;Insert data to students table\
\
&emsp;&emsp;&#8226;&emsp;Insert data to trainers table\
\
&emsp;&emsp;&#8226;&emsp;Insert data to assignments table\
\
&emsp;&emsp;&#8226;&emsp;Insert data to courses table\
\
&emsp;&emsp;&#8226;&emsp;Associate student with course\
\
&emsp;&emsp;&#8226;&emsp;Associate trainer with course\
\
&emsp;&emsp;&#8226;&emsp;Associate student with course's assignment\

Syntetic data are kept in database in case user doesn't want to provide any.

## Demonstration

Starting the application, user will be asked if he/she wants to add/associate data to/from database or proceed to presentation with only the syntetic data.

<img src="screenshots/main-menu.PNG" />

#### Choosing to insert/associate data

User will see a menu with all data insertions or data associations possible to choose from.

<img src="screenshots/insert-or-connect-data-menu.PNG" />

##### Option 1: Insert students.

<img src="screenshots/student-first-name.PNG" />

<img src="screenshots/student-last-name.PNG" />

<img src="screenshots/student-date-of-birth.PNG" />

##### Option 2: Insert trainers. ,
##### Option 3: Insert assignments. ,
##### Option 4: Insert courses.

The procedure is the same for these options as above. User is asked to provide some information before insertion, as he/she did with student.

##### Option 5: Connect student to courses.

Picking option no.5, user must choose first from a list of students

<img src="screenshots/connect-student-to-course-students-list.PNG" />

and then from a list of courses, to insert the association between them to database.

<img src="screenshots/connect-student-to-course-courses-list.PNG" />

After the association is completed, user is asked to add tuition fees of student's enrollment to course.

<img src="screenshots/tuition-fees.PNG" />

If everything is valid the insertion is done.

<img src="screenshots/student-course-completion.PNG" />

If not, the validation error is being displayed and the user is asked if he/she wants to associate this student with another course.

<img src="screenshots/student-course-validation.PNG" />

##### Option 6: Connect trainer to co
