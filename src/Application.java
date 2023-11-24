import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Application {
    // Database connection variables
    private static String url = "jdbc:postgresql://127.0.0.1/studentstable";
    private static String user = "postgres";
    private static String password = "Karachi1!";

    public static void getAllStudents(Connection connection) {
        // JDBC variables
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Executes a query to select all records from the students table
            String query = "SELECT * FROM students";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            // Process the result set and display all the records
            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                java.sql.Date enrollmentDate = resultSet.getDate("enrollment_date");

                // Displays the records
                System.out.println("Student ID: " + studentId);
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.println("Email: " + email);
                System.out.println("Enrollment Date: " + enrollmentDate + "\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes resources in reverse order
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addStudent(Connection connection, String firstName, String lastName, String email, java.sql.Date enrollmentDate) {
        // JDBC variables
        PreparedStatement preparedStatement = null;

        try {
            // Executes a query to insert a new student record
            String query = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);

            // Sets values for the prepared statement
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setDate(4, enrollmentDate);

            // Executes the update
            int rowsAffected = preparedStatement.executeUpdate();

            // Checks if the insertion was successful
            if (rowsAffected > 0) {
                System.out.println("New student inserted.\n");
            } else {
                System.out.println("Failed to insert new student.\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes resources in reverse order
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateStudentEmail(Connection connection, int studentId, String newEmail) {
        // JDBC variables
        PreparedStatement preparedStatement = null;

        try {
            // Executes a query to update the email address for a student
            String query = "UPDATE students SET email = ? WHERE student_id = ?";
            preparedStatement = connection.prepareStatement(query);

            // Sets values for the prepared statement
            preparedStatement.setString(1, newEmail);
            preparedStatement.setInt(2, studentId);

            // Executes the update
            int rowsAffected = preparedStatement.executeUpdate();

            // Checks if the update was successful
            if (rowsAffected > 0) {
                System.out.println("Email address updated.\n");
            } else {
                System.out.println("Failed to update email address.\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes resources in reverse order
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteStudent(Connection connection, int studentId) {
        // JDBC variables
        PreparedStatement preparedStatement = null;

        try {
            // Executes a query to delete the record of a student
            String query = "DELETE FROM students WHERE student_id = ?";
            preparedStatement = connection.prepareStatement(query);

            // Sets value for the prepared statement
            preparedStatement.setInt(1, studentId);

            // Executes the deletion
            int rowsAffected = preparedStatement.executeUpdate();

            // Checks if the deletion was successful
            if (rowsAffected > 0) {
                System.out.println("Student record deleted.\n");
            } else {
                System.out.println("Failed to delete student record.\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes resources in reverse order
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Attempts to connect to database
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Checks if the connection is successful
            if (connection != null) {
                System.out.println("Connected to the PostgreSQL database!");

                // Calls all methods
                getAllStudents(connection);
                addStudent(connection,"Sam","Smith","sam@gmail.com",java.sql.Date.valueOf("2023-01-01"));
                updateStudentEmail(connection, 1, "updated.email.gmail.com");
                deleteStudent(connection,1);
            } else {
                System.out.println("Failed to connect to the database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

