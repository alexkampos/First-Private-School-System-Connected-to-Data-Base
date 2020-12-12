/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static models.Assignment.pickFromCoursesAssignments;
import static models.Course.pickFromStudentsCourses;
import static models.DatabaseConnection.getDB_URL;
import static models.DatabaseConnection.getMYSQL_JDBC_DRIVER;
import static models.DatabaseConnection.getPASSWORD;
import static models.DatabaseConnection.getUSERNAME;
import static models.Student.pickStudent;
import static models.Assignment.pickStudentsAssignment;
import static validations_and_methods.UserInputValidations.validDouble;
import static validations_and_methods.UserInputValidations.yesOrNo;

/**
 *
 * @author Alex K (alexkambos@gmail.com)
 */
public class AssignmentStudentCourse {

    private int assignmentId;
    private String assignmentTitle;
    private String assignmentDescription;
    private double assignmentOralMark;
    private double assignmentTotalMark;
    private LocalDateTime assignmentSubDateTime;
    private String studentFirstName;
    private String studentLastName;
    private LocalDate studentDateOfBirth;
    private String courseTitle;
    private String courseStream;
    private String courseType;
    private LocalDate courseStartDate;
    private LocalDate courseEndDate;
    private Double grade;

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
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

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public AssignmentStudentCourse() {
    }

    /*
    Method that gets assignments per student per course
     */
    public static ArrayList<AssignmentStudentCourse> getAssignmentsPerStudentPerCourse() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        ArrayList<AssignmentStudentCourse> assignmentsPerStudentPerCourseList = new ArrayList<>();

        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            query = "SELECT C.TITLE,C.STREAM,C.TYPE,C.START_DATE,C.END_DATE,S.FIRST_NAME,S.LAST_NAME,S.DATE_OF_BIRTH,A.TITLE,A.DESCRIPTION,A.ORAL_MARK,A.TOTAL_MARK,CSA.GRADE, CA.ASSI_SUB_DATE_TIME "
                    + "FROM COURSE_STUDENT_ASSIGNMENT CSA JOIN STUDENTS S ON S.STUD_ID=CSA.STUD_ID JOIN COURSES C ON C.COURSE_ID=CSA.COURSE_ID "
                    + "JOIN ASSIGNMENTS A ON CSA.ASSIGNMENT_ID=A.ASSIGNMENT_ID JOIN COURSE_ASSIGNMENT CA ON CA.COURSE_ID=C.COURSE_ID AND CA.ASSIGNMENT_ID=A.ASSIGNMENT_ID";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AssignmentStudentCourse assiStudCour = new AssignmentStudentCourse();
                assiStudCour.courseTitle = resultSet.getString("C.TITLE");
                assiStudCour.courseStream = resultSet.getString("C.STREAM");
                assiStudCour.courseType = resultSet.getString("C.TYPE");
                assiStudCour.courseStartDate = resultSet.getObject("C.START_DATE", LocalDate.class);
                assiStudCour.courseEndDate = resultSet.getObject("C.END_DATE", LocalDate.class);
                assiStudCour.studentFirstName = resultSet.getString("S.FIRST_NAME");
                assiStudCour.studentLastName = resultSet.getString("S.LAST_NAME");
                assiStudCour.studentDateOfBirth = resultSet.getObject("S.DATE_OF_BIRTH", LocalDate.class);
                assiStudCour.assignmentTitle = resultSet.getString("A.TITLE");
                assiStudCour.assignmentDescription = resultSet.getString("A.DESCRIPTION");
                assiStudCour.assignmentOralMark = resultSet.getDouble("A.ORAL_MARK");
                assiStudCour.assignmentTotalMark = resultSet.getDouble("A.TOTAL_MARK");
                assiStudCour.grade = (Double) resultSet.getObject("CSA.GRADE");
                assiStudCour.assignmentSubDateTime = resultSet.getObject("CA.ASSI_SUB_DATE_TIME", LocalDateTime.class);
                assignmentsPerStudentPerCourseList.add(assiStudCour);
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
        return assignmentsPerStudentPerCourseList;
    }

    /*
    Method that inserts assignment's grade
     */
    public static void insertAssignmentGrade() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query;
        int studentPick = pickStudent();
        int coursePick = pickFromStudentsCourses(studentPick);
        int assignmentPick = pickStudentsAssignment(studentPick, coursePick);
        System.out.println("\nThe final grade for this student's assignment: ");
        double usersGrade = validDouble();
        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            query = "UPDATE COURSE_STUDENT_ASSIGNMENT SET GRADE = ? WHERE STUD_ID = ? AND ASSIGNMENT_ID = ? AND COURSE_ID = ?;";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, usersGrade);
            preparedStatement.setInt(2, studentPick);
            preparedStatement.setInt(3, assignmentPick);
            preparedStatement.setInt(4, coursePick);
            preparedStatement.executeUpdate();
            System.out.println("Your entry has finished.");

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

    /*
    method where user picks if he wants to add all the assignments to student or specific ones
     */
    public static void pickCourseAssignment() {
        int studentPick = pickStudent();
        int coursePick = pickFromStudentsCourses(studentPick);
        System.out.println("Would you like to add to this student all the assignments of this course? (Be careful, this is going to work only if you haven't added any of the assignments of the course to this student!): ");
        boolean yesOrNo = yesOrNo();
        if (yesOrNo) {
            connectToAllAssignments(studentPick, coursePick);
        } else {
            connectToSpecificAssignments(studentPick, coursePick);
        }
    }

    public static void connectToSpecificAssignments(int studentPick, int coursePick) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        do {
            int assignmentPick = pickFromCoursesAssignments(coursePick);
            try {
                Class.forName(getMYSQL_JDBC_DRIVER());
                connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
                query = "INSERT INTO COURSE_STUDENT_ASSIGNMENT (STUD_ID,ASSIGNMENT_ID,COURSE_ID) VALUES (?,?,?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, studentPick);
                preparedStatement.setInt(2, assignmentPick);
                preparedStatement.setInt(3, coursePick);
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
        } while (repeatAssignmentStudentCourseConnection());
    }

    public static void connectToAllAssignments(int studentPick, int coursePick) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        String query;
        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            query = "{CALL InsertAllCoursesAssignmentsToStudent(?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setInt(1, coursePick);
            callableStatement.setInt(2, studentPick);
            callableStatement.executeUpdate();
            System.out.print("\nYour entry has been completed.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Your entry hasn't been completed (Duplicate Entry)!");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.print("\nSomething went wrong!");
        } finally {
            if (callableStatement != null) {
                try {
                    callableStatement.close();
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

    /*
    method to see assignments of student from specific course
     */
    public static ArrayList<AssignmentStudentCourse> getStudentsCoursesAssignments(int studentPick, int coursePick) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query;
        ArrayList<AssignmentStudentCourse> studentsCoursesAssignments = new ArrayList<>();
        try {
            Class.forName(getMYSQL_JDBC_DRIVER());
            connection = DriverManager.getConnection(getDB_URL(), getUSERNAME(), getPASSWORD());
            query = "SELECT C.TITLE,C.STREAM,C.TYPE,S.FIRST_NAME,S.LAST_NAME,A.ASSIGNMENT_ID,A.TITLE,A.DESCRIPTION,A.ORAL_MARK,A.TOTAL_MARK,CSA.GRADE, CA.ASSI_SUB_DATE_TIME FROM COURSE_STUDENT_ASSIGNMENT CSA JOIN STUDENTS S ON S.STUD_ID=CSA.STUD_ID JOIN COURSES C ON C.COURSE_ID=CSA.COURSE_ID JOIN ASSIGNMENTS A ON CSA.ASSIGNMENT_ID=A.ASSIGNMENT_ID JOIN COURSE_ASSIGNMENT CA ON CA.COURSE_ID=C.COURSE_ID AND CA.ASSIGNMENT_ID=A.ASSIGNMENT_ID WHERE CSA.COURSE_ID = ? AND CSA.STUD_ID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, coursePick);
            preparedStatement.setInt(2, studentPick);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AssignmentStudentCourse assiStudCour = new AssignmentStudentCourse();
                assiStudCour.courseTitle = resultSet.getString("C.TITLE");
                assiStudCour.courseStream = resultSet.getString("C.STREAM");
                assiStudCour.courseType = resultSet.getString("C.TYPE");
                assiStudCour.studentFirstName = resultSet.getString("S.FIRST_NAME");
                assiStudCour.studentLastName = resultSet.getString("S.LAST_NAME");
                assiStudCour.assignmentId = resultSet.getInt("A.ASSIGNMENT_ID");
                assiStudCour.assignmentTitle = resultSet.getString("A.TITLE");
                assiStudCour.assignmentDescription = resultSet.getString("A.DESCRIPTION");
                assiStudCour.assignmentOralMark = resultSet.getDouble("A.ORAL_MARK");
                assiStudCour.assignmentTotalMark = resultSet.getDouble("A.TOTAL_MARK");
                assiStudCour.grade = (Double) resultSet.getObject("CSA.GRADE");
                assiStudCour.assignmentSubDateTime = resultSet.getObject("CA.ASSI_SUB_DATE_TIME", LocalDateTime.class);
                studentsCoursesAssignments.add(assiStudCour);
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
        return studentsCoursesAssignments;
    }

    public static boolean repeatAssignmentStudentCourseConnection() {
        System.out.print("Would you like to add another assignment to this student? (yes or no): ");
        boolean yesOrNo = yesOrNo();
        if (yesOrNo) {
            return true;
        } else {
            return false;
        }
    }
}
