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
import static models.DatabaseConnection.getDB_URL;
import static models.DatabaseConnection.getMYSQL_JDBC_DRIVER;
import static models.DatabaseConnection.getPASSWORD;
import static models.DatabaseConnection.getUSERNAME;
import static private_school.PrivateSchoolDataBase.sc;
import static validations_and_methods.UserInputValidations.isNoNumbersValid;

/**
 *
 * @author Alex K (alexkambos@gmail.com)
 */
public class Trainer {

    private int trainerId;
    private String firstName;
    private String lastName;
    private String subject;

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Trainer() {
    }

    public static void createTrainer() {

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

        System.out.print("\nSubject: ");
        String subject = sc.nextLine();

        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            String query = "INSERT INTO TRAINERS (FIRST_NAME,LAST_NAME,SUBJECT) VALUES (?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, subject);
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

    public static ArrayList<Trainer> getTrainers() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        ArrayList<Trainer> trainersList = new ArrayList<>();
        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            query = "SELECT TRAINER_ID,FIRST_NAME,LAST_NAME,SUBJECT FROM TRAINERS";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Trainer trai = new Trainer();
                trai.trainerId = resultSet.getInt("TRAINER_ID");
                trai.firstName = resultSet.getString("FIRST_NAME");
                trai.lastName = resultSet.getString("LAST_NAME");
                trai.subject = resultSet.getString("SUBJECT");
                trainersList.add(trai);
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
        return trainersList;
    }
}
