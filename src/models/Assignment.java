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
import java.util.ArrayList;
import java.util.HashMap;
import static models.DatabaseConnection.getDB_URL;
import static models.DatabaseConnection.getMYSQL_JDBC_DRIVER;
import static models.DatabaseConnection.getPASSWORD;
import static models.DatabaseConnection.getUSERNAME;
import static models.Procedures.show;
import static models.Procedures.showAssis;
import static private_school.PrivateSchoolDataBase.sc;
import static validations_and_methods.UserInputValidations.validDouble;
import static validations_and_methods.UserInputValidations.validInt;

/**
 *
 * @author Alex K (alexkambos@gmail.com)
 */
public class Assignment {

    private int assignmentId;
    private String title;
    private String description;
    private double oralMark;
    private double totalMark;

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getOralMark() {
        return oralMark;
    }

    public void setOralMark(double oralMark) {
        this.oralMark = oralMark;
    }

    public double getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(double totalMark) {
        this.totalMark = totalMark;
    }

    public Assignment() {
    }

    public static void createAssignment() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        System.out.print("\nTitle: ");
        String title = sc.nextLine();
        sc.nextLine();
        System.out.print("\nDescription: ");
        String description = sc.nextLine();
        System.out.print("\nOral mark: ");
        double oralMark = validDouble();
        System.out.print("\nTotal mark: ");
        double totalMark = validDouble();

        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            String query = "INSERT INTO ASSIGNMENTS (TITLE,DESCRIPTION,ORAL_MARK,TOTAL_MARK) VALUES (?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setDouble(3, oralMark);
            preparedStatement.setDouble(4, totalMark);
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

    public static ArrayList<Assignment> getAssignments() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        ArrayList<Assignment> assignmentsList = new ArrayList<>();
        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            query = "SELECT ASSIGNMENT_ID,TITLE,DESCRIPTION,ORAL_MARK,TOTAL_MARK FROM ASSIGNMENTS";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Assignment assi = new Assignment();
                assi.assignmentId = resultSet.getInt("ASSIGNMENT_ID");
                assi.title = resultSet.getString("TITLE");
                assi.description = resultSet.getString("DESCRIPTION");
                assi.oralMark = resultSet.getDouble("ORAL_MARK");
                assi.totalMark = resultSet.getDouble("TOTAL_MARK");
                assignmentsList.add(assi);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
        return assignmentsList;
    }

    public static int pickFromCoursesAssignments(int courseId) {
        HashMap<Integer, Assignment> pickAssignment = show(courseId, "course");
        System.out.print("\nType the number of the assignment you would like to pick: ");
        int usersChoiceValidation = validInt(pickAssignment);
        int usersChoice = pickAssignment.get(usersChoiceValidation).assignmentId;
        return usersChoice;
    }

    public static int pickStudentsAssignment(int studentPick, int coursePick) {
        HashMap<Integer, AssignmentStudentCourse> pickStudentsAssignment = showAssis(studentPick, coursePick);
        System.out.print("\nType the number of student's assignment you would like to grade: ");
        int usersChoiceValidation = validInt(pickStudentsAssignment);
        int usersChoice = pickStudentsAssignment.get(usersChoiceValidation).getAssignmentId();
        return usersChoice;
    }
}
