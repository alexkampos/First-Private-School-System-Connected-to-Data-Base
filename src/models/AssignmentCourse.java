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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import static models.Course.pickCourse;
import static models.DatabaseConnection.getDB_URL;
import static models.DatabaseConnection.getMYSQL_JDBC_DRIVER;
import static models.DatabaseConnection.getPASSWORD;
import static models.DatabaseConnection.getUSERNAME;
import static models.Procedures.show;
import static private_school.PrivateSchoolDataBase.sc;
import static validations_and_methods.DateMethods.isValidDate;
import static validations_and_methods.UserInputValidations.validInt;
import static validations_and_methods.UserInputValidations.yesOrNo;

/**
 *
 * @author Alex K (alexkambos@gmail.com)
 */
public class AssignmentCourse {

    private String courseTitle;
    private String courseStream;
    private String courseType;
    private LocalDate courseStartDate;
    private LocalDate courseEndDate;
    private String assignmentTitle;
    private String assignmentDescription;
    private double assignmentOralMark;
    private double assignmentTotalMark;
    private LocalDateTime assignmentSubDateTime;

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

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public String getAssignmentDescription() {
        return assignmentDescription;
    }

    public void setAssignmentDescription(String assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }

    public double getAssignmentOralMark() {
        return assignmentOralMark;
    }

    public void setAssignmentOralMark(double assignmentOralMark) {
        this.assignmentOralMark = assignmentOralMark;
    }

    public double getAssignmentTotalMark() {
        return assignmentTotalMark;
    }

    public void setAssignmentTotalMark(double assignmentTotalMark) {
        this.assignmentTotalMark = assignmentTotalMark;
    }

    public LocalDateTime getAssignmentSubDateTime() {
        return assignmentSubDateTime;
    }

    public void setAssignmentSubDateTime(LocalDateTime assignmentSubDateTime) {
        this.assignmentSubDateTime = assignmentSubDateTime;
    }

    public static ArrayList<AssignmentCourse> getAssignmentsPerCourse() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        ArrayList<AssignmentCourse> assignmentsPerCourse = new ArrayList<>();
        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            query = "SELECT C.TITLE,C.STREAM,C.TYPE,C.START_DATE,C.END_DATE,A.TITLE,A.DESCRIPTION,A.ORAL_MARK,A.TOTAL_MARK,CA.ASSI_SUB_DATE_TIME FROM COURSES C JOIN COURSE_ASSIGNMENT CA ON C.COURSE_ID=CA.COURSE_ID JOIN ASSIGNMENTS A ON A.ASSIGNMENT_ID=CA.ASSIGNMENT_ID";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AssignmentCourse assiCour = new AssignmentCourse();
                assiCour.courseTitle = resultSet.getString("C.TITLE");
                assiCour.courseStream = resultSet.getString("C.STREAM");
                assiCour.courseType = resultSet.getString("C.TYPE");
                assiCour.courseStartDate = resultSet.getObject("C.START_DATE", LocalDate.class);
                assiCour.courseEndDate = resultSet.getObject("C.END_DATE", LocalDate.class);
                assiCour.assignmentTitle = resultSet.getString("A.TITLE");
                assiCour.assignmentDescription = resultSet.getString("A.DESCRIPTION");
                assiCour.assignmentOralMark = resultSet.getDouble("A.ORAL_MARK");
                assiCour.assignmentTotalMark = resultSet.getDouble("A.TOTAL_MARK");
                assiCour.assignmentSubDateTime = resultSet.getObject("CA.ASSI_SUB_DATE_TIME", LocalDateTime.class);
                assignmentsPerCourse.add(assiCour);
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
        return assignmentsPerCourse;
    }

    public static int pickAssignment() {
        HashMap<Integer, Assignment> pickAssignment = show("assignment");
        System.out.print("\nType the number of the assignment you would like to pick: ");
        int usersChoiceValidation = validInt(pickAssignment);
        int usersChoice = pickAssignment.get(usersChoiceValidation).getAssignmentId();
        return usersChoice;
    }

    public static void connectAssignmentToCourse() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        int coursePick = pickCourse("\nType the number of the course you would like to add the assignment to: ");
        do {
            int assignmentPick = pickAssignment();
            try {
                Class.forName(getMYSQL_JDBC_DRIVER());
                connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());

                LocalDateTime subDateTime = null;
                while (true) {
                    System.out.print("\nSubmission date and time (dd-mm-yyyy hh:mm): ");
                    String subDateTimeCheck = sc.next();
                    if (!isValidDate(subDateTimeCheck)) {
                        System.out.print("\nWrong input. Please give the right pattern of date and time as shown in the example.");
                    } else {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                        subDateTime = LocalDateTime.parse(subDateTimeCheck, formatter);
                        break;
                    }
                }
                query = "INSERT INTO COURSE_ASSIGNMENT (COURSE_ID,ASSIGNMENT_ID,ASSI_SUB_DATE_TIME) VALUES (?,?,?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, coursePick);
                preparedStatement.setInt(2, assignmentPick);
                preparedStatement.setObject(3, subDateTime);
                preparedStatement.executeUpdate();
                System.out.print("\nYour entry has been completed.");
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
        } while (repeatAssignmentToCourseConnection());
    }

    public static boolean repeatAssignmentToCourseConnection() {
        System.out.print("Would you like to add another assignment to this course? (yes or no): ");
        boolean yesOrNo = yesOrNo();
        if (yesOrNo) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<Assignment> getCoursesAssignments(int courseId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        ArrayList<Assignment> coursesAssignmentsList = new ArrayList<>();
        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            query = "SELECT A.TITLE,A.DESCRIPTION,A.ORAL_MARK,A.TOTAL_MARK FROM ASSIGNMENTS A JOIN COURSE_ASSIGNMENT CA ON A.ASSIGNMENT_ID=CA.ASSIGNMENT_ID WHERE CA.COURSE_ID = ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, courseId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Assignment coursesAssignment = new Assignment();
                coursesAssignment.setTitle(resultSet.getString("A.TITLE"));
                coursesAssignment.setDescription(resultSet.getString("A.DESCRIPTION"));
                coursesAssignment.setOralMark(resultSet.getDouble("A.ORAL_MARK"));
                coursesAssignment.setTotalMark(resultSet.getDouble("A.TOTAL_MARK"));
                coursesAssignmentsList.add(coursesAssignment);
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
        return coursesAssignmentsList;
    }
}
