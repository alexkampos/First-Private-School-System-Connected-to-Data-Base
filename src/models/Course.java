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
import static validations_and_methods.UserInputValidations.validInt;

/**
 *
 * @author Alex K (alexkambos@gmail.com)
 */
public class Course {

    private int courseId;
    private String title;
    private String stream;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Course() {
    }

    public static void createCourse() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        System.out.print("\nTitle: ");
        String title = sc.nextLine();
        sc.nextLine();
        System.out.print("\nType: ");
        String type = sc.nextLine();
        sc.nextLine();
        System.out.print("\nStream: ");
        String stream = sc.nextLine();

        LocalDate startDate = null;
        while (true) {
            System.out.print("\nStart date (dd-mm-yyyy): ");
            String startDateCheck = sc.next();
            if (!isValidDate(startDateCheck)) {
                System.out.print("\nWrong input. Please give the right pattern of date as shown in the example.");
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(startDateCheck);
                startDate = LocalDate.parse(startDateCheck, formatter);
                break;
            }
        }
        LocalDate endDate = null;
        while (true) {
            System.out.print("End date (dd-mm-yyyy): ");
            String endDateCheck = sc.next();
            if (!isValidDate(endDateCheck)) {
                System.out.print("\nWrong input. Please give the right pattern of date as shown in the example.");
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(endDateCheck);
                endDate = LocalDate.parse(endDateCheck, formatter);
                break;
            }

        }

        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            String query = "INSERT INTO COURSES (TITLE,STREAM,TYPE,START_DATE,END_DATE) VALUES (?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, stream);
            preparedStatement.setString(3, type);
            preparedStatement.setObject(4, startDate);
            preparedStatement.setObject(5, endDate);
            int result = preparedStatement.executeUpdate();
            System.out.println(result + " row affected.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.print("\nSomething went wrong!");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static ArrayList<Course> getCourses() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        ArrayList<Course> coursesList = new ArrayList<>();
        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            query = "SELECT COURSE_ID,TITLE,STREAM,TYPE,START_DATE,END_DATE FROM COURSES";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Course cour = new Course();
                cour.courseId = resultSet.getInt("COURSE_ID");
                cour.title = resultSet.getString("TITLE");
                cour.stream = resultSet.getString("STREAM");
                cour.type = resultSet.getString("TYPE");
                cour.startDate = resultSet.getObject("START_DATE", LocalDate.class);
                cour.endDate = resultSet.getObject("END_DATE", LocalDate.class);
                coursesList.add(cour);
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Your entry hasn't been completed (Duplicate Entry)!");

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
        return coursesList;
    }

    public static int pickCourse(String s) {
        HashMap<Integer, Course> pickCourse = show("course");
        System.out.print(s);
        int usersChoiceValidation = validInt(pickCourse);
        int usersChoice = pickCourse.get(usersChoiceValidation).courseId;
        return usersChoice;
    }

    public static int pickFromStudentsCourses(int studId) {
        HashMap<Integer, Course> pickCourse = show(studId, "stud");
        System.out.print("\nType the number of the course you would like to pick: ");
        int usersChoiceValidation = validInt(pickCourse);
        int usersChoice = pickCourse.get(usersChoiceValidation).courseId;
        return usersChoice;
    }
}
