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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import static models.DatabaseConnection.getDB_URL;
import static models.DatabaseConnection.getMYSQL_JDBC_DRIVER;
import static models.DatabaseConnection.getPASSWORD;
import static models.DatabaseConnection.getUSERNAME;
import static models.Procedures.show;
import static private_school.PrivateSchoolDataBase.sc;
import static validations_and_methods.DateMethods.isValidDate;
import static validations_and_methods.UserInputValidations.isNoNumbersValid;
import static validations_and_methods.UserInputValidations.validInt;

/**
 *
 * @author Alex K (alexkambos@gmail.com)
 */
public class Student {

    private int studId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int numberOfCourses;

    public int getStudId() {
        return studId;
    }

    public void setStudId(int newStudId) {
        studId = newStudId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String newFirstName) {
        firstName = newFirstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String newLastName) {
        lastName = newLastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate newDateOfBirth) {
        dateOfBirth = newDateOfBirth;
    }

    public int getNumberOfCourses() {
        return numberOfCourses;
    }

    public void setNumberOfCourses(int numberOfCourses) {
        this.numberOfCourses = numberOfCourses;
    }

    public Student() {
    }

    public static int pickStudent() {
        HashMap<Integer, Student> pickStudent = show("student");
        System.out.print("\nType the number of the student you would like to pick: ");
        int usersChoiceValidation = validInt(pickStudent);
        int usersChoice = pickStudent.get(usersChoiceValidation).getStudId();
        return usersChoice;
    }

    public static void createStudent() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String firstName = null;
        while (true) {
            System.out.print("\nFirst name: ");
            firstName = sc.next();
            if (!isNoNumbersValid(firstName)) {
                System.out.print("\nWrong input. Only letters are allowed on this field.");
            } else {
                break;
            }
        }

        String lastName = null;
        while (true) {
            System.out.print("\nLast name: ");
            lastName = sc.next();
            if (!isNoNumbersValid(lastName)) {
                System.out.print("\nWrong input. Only letters are allowed on this field.");
            } else {
                break;
            }
        }

        LocalDate dateOfBirth = null;
        while (true) {
            System.out.print("\nDate of birth(e.g. dd-mm-yyyy): ");
            String dateOfBirthCheck = sc.next();
            if (!isValidDate(dateOfBirthCheck)) {
                System.out.print("\nWrong input. Please give the right pattern of date as shown in the example.");
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                dateOfBirth = LocalDate.parse(dateOfBirthCheck, formatter);
                break;
            }
        }

        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            String query = "INSERT INTO STUDENTS (FIRST_NAME,LAST_NAME,DATE_OF_BIRTH) VALUES (?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setObject(3, dateOfBirth);
            int result = preparedStatement.executeUpdate();
            System.out.println(result + " row affected.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Your entry hasn't been completed (Duplicate Entry)!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.print("\nSomething went wrong!");
        } finally {
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

    }

    public static ArrayList<Student> getStudents() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        ArrayList<Student> studentsList = new ArrayList<>();
        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            query = "SELECT STUD_ID,FIRST_NAME,LAST_NAME,DATE_OF_BIRTH FROM STUDENTS";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Student stud = new Student();
                stud.studId = resultSet.getInt("STUD_ID");
                stud.firstName = resultSet.getString("FIRST_NAME");
                stud.lastName = resultSet.getString("LAST_NAME");
                stud.dateOfBirth = resultSet.getObject("DATE_OF_BIRTH", LocalDate.class);
                studentsList.add(stud);
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
        return studentsList;
    }

    public static ArrayList<Student> getMoreThanOneCourseStudents() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        ArrayList<Student> moreThanOneCourseStudsList = new ArrayList<>();
        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            query = "SELECT FIRST_NAME,LAST_NAME,DATE_OF_BIRTH,COUNT(SC.STUD_ID) AS COURSES FROM STUDENTS S JOIN STUDENT_COURSE SC ON S.STUD_ID=SC.STUD_ID GROUP BY SC.STUD_ID HAVING COURSES>1";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Student stud = new Student();
                stud.firstName = resultSet.getString("FIRST_NAME");
                stud.lastName = resultSet.getString("LAST_NAME");
                stud.dateOfBirth = resultSet.getObject("DATE_OF_BIRTH", LocalDate.class);
                stud.numberOfCourses = resultSet.getInt("COUNT(SC.STUD_ID)");
                moreThanOneCourseStudsList.add(stud);
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
        return moreThanOneCourseStudsList;
    }
}
