package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection = null;

    // Private constructor to prevent instantiation
    private MySQLConnection() { }

    // Public method to provide access to the singleton instance
    public static Connection getConnection() {
        if (connection == null) {
            synchronized (MySQLConnection.class) {
                if (connection == null) {
                    try {
                        // Load MySQL JDBC Driver
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        // Establish the connection
                        connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }

    // Method to close the connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to test the connection
    public static boolean testConnection() {
        try (Connection testConnection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            return testConnection != null && !testConnection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        // Test the database connection
        if (MySQLConnection.testConnection()) {
            System.out.println("Connection to MySQL database is successful.");
        } else {
            System.out.println("Failed to connect to MySQL database.");
        }
    }
}
