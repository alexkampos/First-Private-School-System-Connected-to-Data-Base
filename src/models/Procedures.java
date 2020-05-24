/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.HashMap;
import static models.Assignment.createAssignment;
import static models.Assignment.getAssignments;
import static models.AssignmentCourse.connectAssignmentToCourse;
import static models.AssignmentCourse.getAssignmentsPerCourse;
import static models.AssignmentCourse.getCoursesAssignments;
import static models.AssignmentStudentCourse.getAssignmentsPerStudentPerCourse;
import static models.AssignmentStudentCourse.getStudentsCoursesAssignments;
import static models.AssignmentStudentCourse.insertAssignmentGrade;
import static models.AssignmentStudentCourse.pickCourseAssignment;
import static models.Course.createCourse;
import static models.Course.getCourses;
import static models.Student.createStudent;
import static models.Student.getMoreThanOneCourseStudents;
import static models.Student.getStudents;
import static models.StudentCourse.connectStudentToCourse;
import static models.StudentCourse.getStudentsCourses;
import static models.StudentCourse.getStudentsPerCourse;
import static models.Trainer.createTrainer;
import static models.Trainer.getTrainers;
import static models.TrainerCourse.connectTrainerToCourse;
import static models.TrainerCourse.getTrainersPerCourse;
import static validations_and_methods.DateMethods.dateTimeToString;
import static validations_and_methods.DateMethods.dateToString;
import static validations_and_methods.UserInputValidations.validInt;

/**
 *
 * @author Alex K (alexkambos@gmail.com)
 */
public class Procedures {

    public static void insertOrSelect() {
        int usersChoice;
        do {
            System.out.println("\n                         MAIN MENU        ");
            System.out.println("----------------------------------------------------------------");
            System.out.println("\nChoose an option:");
            System.out.println("\n1. Insert new data, or connect existing data.  ");
            System.out.println("2. Select a presentation of data.");
            System.out.println("3. End program.");
            usersChoice = validInt();
            if (usersChoice == 1) {
                pickInsert();
            } else if (usersChoice == 2) {
                pickPresentation();
            } else if (usersChoice == 3) {
                break;
            } else {
                System.out.println("Not a valid choice! Try again.");
            }
        } while (true);
        endProgram();
    }

    public static void pickInsert() {
        int usersChoice = 0;
        do {
            System.out.println("\nPick an insert or connection:");
            System.out.println("\n1. Insert students.");
            System.out.println("2. Insert trainers.");
            System.out.println("3. Insert courses.");
            System.out.println("4. Insert assignments.");
            System.out.println("5. Connect student to courses.");
            System.out.println("6. Connect trainer to courses.");
            System.out.println("7. Connect assignment to courses.");
            System.out.println("8. Connect student to course's assignments.");
            System.out.println("9. Insert grade to a student's assignment.");
            System.out.println("10. Back to main menu.");
            usersChoice = validInt();
            if (usersChoice == 1) {
                createStudent();
            } else if (usersChoice == 2) {
                createTrainer();
            } else if (usersChoice == 3) {
                createCourse();
            } else if (usersChoice == 4) {
                createAssignment();
            } else if (usersChoice == 5) {
                connectStudentToCourse();
            } else if (usersChoice == 6) {
                connectTrainerToCourse();
            } else if (usersChoice == 7) {
                connectAssignmentToCourse();
            } else if (usersChoice == 8) {
                pickCourseAssignment();
            } else if (usersChoice == 9) {
                insertAssignmentGrade();
            } else if (usersChoice == 10) {
                break;
            } else {
                System.out.println("Not a valid choice! Try again.");
            }
        } while (true);
        insertOrSelect();
    }

    public static void pickPresentation() {
        int usersChoice = 0;
        do {
            System.out.println("\nPick a presentation:");
            System.out.println("\n1. Students.");
            System.out.println("2. Trainers.");
            System.out.println("3. Courses.");
            System.out.println("4. Assignments.");
            System.out.println("5. Students per course.");
            System.out.println("6. Trainers per course.");
            System.out.println("7. Assignments per course.");
            System.out.println("8. Assignments per student per course.");
            System.out.println("9. Students enrolled to more than one courses.");
            System.out.println("10. Back to main menu.");
            usersChoice = validInt();
            if (usersChoice == 1) {
                show("student");
            } else if (usersChoice == 2) {
                show("trainer");
            } else if (usersChoice == 3) {
                show("course");
            } else if (usersChoice == 4) {
                show("assignment");
            } else if (usersChoice == 5) {
                show("students per course");
            } else if (usersChoice == 6) {
                show("trainers per course");
            } else if (usersChoice == 7) {
                show("assignments per course");
            } else if (usersChoice == 8) {
                show("assignments per student per course");
            } else if (usersChoice == 9) {
                show("more than one course students");
            } else if (usersChoice == 10) {
                break;
            } else {
                System.out.println("Not a valid choice! Try again.");
            }
        } while (true);
        insertOrSelect();
    }

    public static void endProgram() {
        System.out.println("See you next time.");
        System.exit(0);
    }

    /*
    Method to present elements of the specified table of the database
     */
    public static HashMap show(String choice) {
        int counter = 1;
        switch (choice) {
            case "course":
                ArrayList<Course> coursesList = getCourses();
                HashMap<Integer, Course> coursesMap = new HashMap<>();
                for (Course course : coursesList) {
                    coursesMap.put(counter, course);
                    System.out.println("\n" + counter + ". Title: " + course.getTitle() + ", Stream: " + course.getStream() + ", Type: " + course.getType() + ", Start date: " + dateToString(course.getStartDate()) + ", End date: " + dateToString(course.getEndDate()));
                    counter++;
                }
                return coursesMap;
            case "student":
                ArrayList<Student> studentsList = getStudents();
                HashMap<Integer, Student> studentsMap = new HashMap<>();
                for (Student student : studentsList) {
                    studentsMap.put(counter, student);
                    System.out.println("\n" + counter + ". First name: " + student.getFirstName() + ", Last name: " + student.getLastName() + ", Date of birth: " + dateToString(student.getDateOfBirth()));
                    counter++;
                }
                return studentsMap;
            case "trainer":
                ArrayList<Trainer> trainersList = getTrainers();
                HashMap<Integer, Trainer> trainersMap = new HashMap<>();
                for (Trainer trainer : trainersList) {
                    trainersMap.put(counter, trainer);
                    System.out.println("\n" + counter + ". First name: " + trainer.getFirstName() + ", Last name: " + trainer.getLastName() + ", Subject: " + trainer.getSubject());
                    counter++;
                }
                return trainersMap;
            case "assignment":
                ArrayList<Assignment> assignmentsList = getAssignments();
                HashMap<Integer, Assignment> assignmentsMap = new HashMap<>();
                for (Assignment assignment : assignmentsList) {
                    assignmentsMap.put(counter, assignment);
                    System.out.println("\n" + counter + ". Title: " + assignment.getTitle() + ", Description: " + assignment.getDescription() + ", Oral mark: " + assignment.getOralMark() + ", Total mark: " + assignment.getTotalMark());
                    counter++;
                }
                return assignmentsMap;
            case "students per course":
                ArrayList<StudentCourse> studCourList = getStudentsPerCourse();
                for (StudentCourse sc : studCourList) {
                    System.out.println("\n" + counter + ". Title: " + sc.getCourseTitle() + ", Stream: " + sc.getCourseStream() + ", Type: " + sc.getCourseType() + ", Start date: " + dateToString(sc.getCourseStartDate())
                            + ", End date: " + dateToString(sc.getCourseEndDate()) + ", First name: " + sc.getStudentFirstName() + ", Last name: " + sc.getStudentLastName() + ", Date of birth: " + dateToString(sc.getStudentDateOfBirth()) + ", Tuition fees: " + sc.getTuitionFees());
                    counter++;
                }
                break;
            case "trainers per course":
                ArrayList<TrainerCourse> traiCourList = getTrainersPerCourse();
                for (TrainerCourse tc : traiCourList) {
                    System.out.println("\n" + counter + ". Title: " + tc.getCourseTitle() + ", Stream: " + tc.getCourseStream() + ", Type: " + tc.getCourseType() + ", Start date: "
                            + dateToString(tc.getCourseStartDate()) + ", End date: " + dateToString(tc.getCourseEndDate()) + ", First name: " + tc.getTrainerFirstName() + ", Last name: " + tc.getTrainerLastName() + ", Subject: " + tc.getTrainerSubject());
                    counter++;
                }
                break;
            case "assignments per course":
                ArrayList<AssignmentCourse> assiCourList = getAssignmentsPerCourse();
                for (AssignmentCourse ac : assiCourList) {
                    System.out.println("\n" + counter + ". Course title: " + ac.getCourseTitle() + ", Stream: " + ac.getCourseStream() + ", Type: " + ac.getCourseType() + ", Start date: " + dateToString(ac.getCourseStartDate()) + ", End date: " + dateToString(ac.getCourseEndDate())
                            + ", Assignment title: " + ac.getAssignmentTitle() + ", Description: " + ac.getAssignmentDescription() + ", Oral mark: " + ac.getAssignmentOralMark() + ", Total mark: " + ac.getAssignmentTotalMark() + ", Submission date and time: " + dateTimeToString(ac.getAssignmentSubDateTime()));
                    counter++;
                }
                break;
            case "assignments per student per course":
                ArrayList<AssignmentStudentCourse> assiStudCourList = getAssignmentsPerStudentPerCourse();
                for (AssignmentStudentCourse asc : assiStudCourList) {
                    System.out.println("\n" + counter + ". Course title: " + asc.getCourseTitle() + ", Stream: " + asc.getCourseStream() + ", Type: " + asc.getCourseType() + ", Start date: " + dateToString(asc.getCourseStartDate()) + ", End date: " + dateToString(asc.getCourseEndDate()) + ", Student first name: "
                            + asc.getStudentFirstName() + ", Last name: " + asc.getStudentLastName() + ", Date of birth: " + dateToString(asc.getStudentDateOfBirth()) + ", Assignment title: " + asc.getAssignmentTitle() + ", Description: " + asc.getAssignmentDescription()
                            + ", Oral Mark: " + asc.getAssignmentOralMark() + ", Total mark: " + asc.getAssignmentTotalMark() + ", Submission date and time: " + dateTimeToString(asc.getAssignmentSubDateTime()) + ", Grade: " + asc.getGrade());
                    counter++;
                }
                break;
            case "more than one course students":
                ArrayList<Student> moreThanOneCourseStudsList = getMoreThanOneCourseStudents();
                for (Student student : moreThanOneCourseStudsList) {
                    System.out.println("\n" + counter + ". First name: " + student.getFirstName() + ", Last name: " + student.getLastName() + ", Date of birth: " + dateToString(student.getDateOfBirth()) + ", Number of courses: " + student.getNumberOfCourses());
                    counter++;
                }
                break;
            default:
                return null;
        }
        return null;
    }

    /*
    Method to present elements ftom specified tablefrom database based on id
     */
    public static HashMap show(int id, String pick) {
        int counter = 1;
        switch (pick) {
            case "stud":
                ArrayList<Course> studentsCoursesList = getStudentsCourses(id);
                HashMap<Integer, Course> courseMap = new HashMap<>();
                for (Course course : studentsCoursesList) {
                    courseMap.put(counter, course);
                    System.out.println("\n" + counter + ". Title: " + course.getTitle() + ", Stream: " + course.getStream() + ", Type: " + course.getType() + ", Start date: " + dateToString(course.getStartDate()) + ", End date: " + dateToString(course.getEndDate()));
                    counter++;
                }
                return courseMap;
            case "course":
                ArrayList<Assignment> courseAssignmentsList = getCoursesAssignments(id);
                HashMap<Integer, Assignment> assignmentMap = new HashMap<>();
                for (Assignment assignment : courseAssignmentsList) {
                    assignmentMap.put(counter, assignment);
                    System.out.println("\n" + counter + ". Title: " + assignment.getTitle() + ", Description: " + assignment.getDescription() + ", Oral mark: " + assignment.getOralMark() + ", Total mark: " + assignment.getTotalMark());
                    counter++;
                }
                return assignmentMap;
            default:
                return null;
        }

    }

    /*
    MEthod to present elements from specified table on database based on student pick and course pick by user
     */
    public static HashMap showAssis(int studentPick, int coursePick) {
        int counter = 1;
        ArrayList<AssignmentStudentCourse> studsAssiList = getStudentsCoursesAssignments(studentPick, coursePick);
        HashMap<Integer, AssignmentStudentCourse> studsAssiMap = new HashMap<>();
        for (AssignmentStudentCourse asc : studsAssiList) {
            if (asc.getGrade() == null) {
                studsAssiMap.put(counter, asc);
                System.out.println("\n" + counter + ". Course title: " + asc.getCourseTitle() + ", Stream: " + asc.getCourseStream() + ", Type: " + asc.getCourseType() + ", Student first name: "
                        + asc.getStudentFirstName() + ", Last name: " + asc.getStudentLastName() + ", Assignment title: " + asc.getAssignmentTitle() + ", Description: " + asc.getAssignmentDescription()
                        + ", Oral Mark: " + asc.getAssignmentOralMark() + ", Total mark: " + asc.getAssignmentTotalMark() + ", Submission date and time: " + dateTimeToString(asc.getAssignmentSubDateTime()) + ", Grade: " + asc.getGrade());
                counter++;
            }
        }
        return studsAssiMap;
    }

}
