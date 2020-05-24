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
import java.util.HashMap;
import static models.Course.pickCourse;
import static models.DatabaseConnection.getDB_URL;
import static models.DatabaseConnection.getMYSQL_JDBC_DRIVER;
import static models.DatabaseConnection.getPASSWORD;
import static models.DatabaseConnection.getUSERNAME;
import static models.Procedures.show;
import static validations_and_methods.UserInputValidations.validInt;
import static validations_and_methods.UserInputValidations.yesOrNo;

/**
 *
 * @author Alex K (alexkambos@gmail.com)
 */
public class TrainerCourse {

    private String courseTitle;
    private String courseStream;
    private String courseType;
    private LocalDate courseStartDate;
    private LocalDate courseEndDate;
    private String trainerFirstName;
    private String trainerLastName;
    private String trainerSubject;

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

    public String getTrainerFirstName() {
        return trainerFirstName;
    }

    public void setTrainerFirstName(String trainerFirstName) {
        this.trainerFirstName = trainerFirstName;
    }

    public String getTrainerLastName() {
        return trainerLastName;
    }

    public void setTrainerLastName(String trainerLastName) {
        this.trainerLastName = trainerLastName;
    }

    public String getTrainerSubject() {
        return trainerSubject;
    }

    public void setTrainerSubject(String trainerSubject) {
        this.trainerSubject = trainerSubject;
    }

    public TrainerCourse() {
    }

    public static ArrayList<TrainerCourse> getTrainersPerCourse() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        ArrayList<TrainerCourse> trainersPerCourse = new ArrayList<>();
        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            query = "SELECT C.TITLE,C.STREAM,C.TYPE,C.START_DATE,C.END_DATE,T.FIRST_NAME,T.LAST_NAME,T.SUBJECT FROM TRAINERS T JOIN TRAINER_COURSE  TC ON T.TRAINER_ID=TC.TRAINER_ID JOIN COURSES C ON C.COURSE_ID=TC.COURSE_ID";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TrainerCourse traiCour = new TrainerCourse();
                traiCour.courseTitle = resultSet.getString("C.TITLE");
                traiCour.courseStream = resultSet.getString("C.STREAM");
                traiCour.courseType = resultSet.getString("C.TYPE");
                traiCour.courseStartDate = resultSet.getObject("C.START_DATE", LocalDate.class);
                traiCour.courseEndDate = resultSet.getObject("C.END_DATE", LocalDate.class);
                traiCour.trainerFirstName = resultSet.getString("T.FIRST_NAME");
                traiCour.trainerLastName = resultSet.getString("T.LAST_NAME");
                traiCour.trainerSubject = resultSet.getString("T.SUBJECT");
                trainersPerCourse.add(traiCour);
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
        return trainersPerCourse;
    }

    public static int pickTrainer() {
        HashMap<Integer, Trainer> pickTrainer = show("trainer");
        System.out.print("\nType the number of the trainer you would like to pick: ");
        int usersChoiceValidation = validInt(pickTrainer);
        int usersChoice = pickTrainer.get(usersChoiceValidation).getTrainerId();
        return usersChoice;
    }

    public static void connectTrainerToCourse() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        int trainerPick = pickTrainer();
        do {
            int coursePick = pickCourse("\nType the number of the course you would like to add the trainer to: ");
            try {
                Class.forName(getMYSQL_JDBC_DRIVER());
                connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
                query = "INSERT INTO TRAINER_COURSE (COURSE_ID,TRAINER_ID) VALUES (?,?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, coursePick);
                preparedStatement.setInt(2, trainerPick);
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
        } while (repeatTrainerToCourseConnection());

    }

    public static boolean repeatTrainerToCourseConnection() {
        System.out.print("Would you like to add another course to this trainer (yes or no): ");
        boolean yesOrNo = yesOrNo();
        if (yesOrNo) {
            return true;
        } else {
            return false;
        }
    }
}
