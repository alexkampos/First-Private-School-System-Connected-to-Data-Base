/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Alex K (alexkambos@gmail.com)
 */
public class DatabaseConnection {

    private static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/private_school?serverTimezone=UTC";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    public static String getMYSQL_JDBC_DRIVER() {
        return MYSQL_JDBC_DRIVER;
    }

    public static String getDB_URL() {
        return DB_URL;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

}
