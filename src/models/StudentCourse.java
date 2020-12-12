/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import static models.Course.pickCourse;
import static models.DatabaseConnection.getDB_URL;
import static models.DatabaseConnection.getMYSQL_JDBC_DRIVER;
import static models.DatabaseConnection.getPASSWORD;
import static models.DatabaseConnection.getUSERNAME;
import static models.Student.pickStudent;
import static validations_and_methods.UserInputValidations.validDouble;
import static validations_and_methods.UserInputValidations.yesOrNo;

/**
 *
 * @author Alex K (alexkambos@gmail.com)
 */
public class StudentCourse {

    private String courseTitle;
    private String courseStream;
    private String courseType;
    private LocalDate courseStartDate;
    private LocalDate courseEndDate;
    private String studentFirstName;
    private String studentLastName;
    private LocalDate studentDateOfBirth;
    private double tuitionFees;

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseStream() {
        return courseStream;
    }

    public void setCourseStream(String courseStream) {
        this.courseStream = courseStream;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public LocalDate getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(LocalDate courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public LocalDate getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(LocalDate courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public LocalDate getStudentDateOfBirth() {
        return studentDateOfBirth;
    }

    public void setStudentDateOfBirth(LocalDate studentDateOfBirth) {
        this.studentDateOfBirth = studentDateOfBirth;
    }

    public double getTuitionFees() {
        return tuitionFees;
    }

    public void setTuitionFees(double tuitionFees) {
        this.tuitionFees = tuitionFees;
    }

    public StudentCourse() {
    }

    public static ArrayList<StudentCourse> getStudentsPerCourse() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        ArrayList<StudentCourse> studentsPerCourse = new ArrayList<>();

        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            query = "SELECT C.TITLE,C.STREAM,C.TYPE,C.START_DATE,C.END_DATE,S.FIRST_NAME,S.LAST_NAME,S.DATE_OF_BIRTH,SC.TUITION_FEES FROM STUDENTS S JOIN STUDENT_COURSE SC ON S.STUD_ID=SC.STUD_ID JOIN COURSES C ON C.COURSE_ID=SC.COURSE_ID";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                StudentCourse studCour = new StudentCourse();
                studCour.courseTitle = resultSet.getString("C.TITLE");
                studCour.courseStream = resultSet.getString("C.STREAM");
                studCour.courseType = resultSet.getString("C.TYPE");
                studCour.courseStartDate = resultSet.getObject("C.START_DATE", LocalDate.class);
                studCour.courseEndDate = resultSet.getObject("C.END_DATE", LocalDate.class);
                studCour.studentFirstName = resultSet.getString("S.FIRST_NAME");
                studCour.studentLastName = resultSet.getString("S.LAST_NAME");
                studCour.studentDateOfBirth = resultSet.getObject("S.DATE_OF_BIRTH", LocalDate.class);
                studCour.tuitionFees = resultSet.getDouble("SC.TUITION_FEES");
                studentsPerCourse.add(studCour);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print("\nSomething went wrong!");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return studentsPerCourse;
    }

    public static void connectStudentToCourse() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        int studentPick = pickStudent();
        do {
            int coursePick = pickCourse("\nType the number of the course you would like to add the student to: ");
            try {
                Class.forName(getMYSQL_JDBC_DRIVER());
                connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
                System.out.print("Tuition fees: ");
                double tuitionFees = validDouble();
                query = "INSERT INTO STUDENT_COURSE (STUD_ID,COURSE_ID,TUITION_FEES) VALUES (?,?,?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, studentPick);
                preparedStatement.setInt(2, coursePick);
                preparedStatement.setDouble(3, tuitionFees);
                preparedStatement.executeUpdate();
                System.out.print("\nYour entry has been completed.");
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("Your entry hasn't been completed (Duplicate Entry)!");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.print("\nYour entry hasn't been completed (Something went wrong)!");
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                    }
                }
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                    }
                }
            }
        } while (repeatStudentToCourseConnection());
    }

    public static boolean repeatStudentToCourseConnection() {
        System.out.print("Would you like to add another course to this student? (yes or no): ");
        boolean yesOrNo = yesOrNo();
        if (yesOrNo) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<Course> getStudentsCourses(int studId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        ArrayList<Course> studentsCoursesList = new ArrayList<>();
        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            query = "SELECT C.TITLE,C.STREAM,C.TYPE,C.START_DATE,C.END_DATE FROM COURSES C  JOIN STUDENT_COURSE SC ON C.COURSE_ID=SC.COURSE_ID WHERE SC.STUD_ID = ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Course studentsCourse = new Course();
                studentsCourse.setTitle(resultSet.getString("C.TITLE"));
                studentsCourse.setStream(resultSet.getString("C.STREAM"));
                studentsCourse.setType(resultSet.getString("C.TYPE"));
                studentsCourse.setStartDate(resultSet.getObject("C.START_DATE", LocalDate.class));
                studentsCourse.setEndDate(resultSet.getObject("C.END_DATE", LocalDate.class));
                studentsCoursesList.add(studentsCourse);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print("\nSomething went wrong!");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return studentsCoursesList;
    }
}
