package migration;

import connection.MySQLConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseMigration {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            // Get the singleton connection instance
            connection = MySQLConnection.getConnection();
            statement = connection.createStatement();

            // Create Database
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS banking_system";
            statement.executeUpdate(createDatabaseSQL);
            System.out.println("Database created or already exists.");

            // Use the created database
            connection.setCatalog("banking_system");

            // Create Accounts Table
            String createAccountsTableSQL = "CREATE TABLE IF NOT EXISTS accounts (" +
                    "account_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "account_holder_name VARCHAR(255), " +
                    "balance DECIMAL(10, 2) DEFAULT 0.0)";
            statement.executeUpdate(createAccountsTableSQL);
            System.out.println("Accounts table created or already exists.");

            // Create Transactions Table
            String createTransactionsTableSQL = "CREATE TABLE IF NOT EXISTS transactions (" +
                    "transaction_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "account_id INT, " +
                    "transaction_type ENUM('DEPOSIT', 'WITHDRAWAL', 'TRANSFER'), " +
                    "amount DECIMAL(10, 2), " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (account_id) REFERENCES Accounts(account_id))";
            statement.executeUpdate(createTransactionsTableSQL);
            System.out.println("Transactions table created or already exists.");
        } catch (SQLException e) {
            System.out.println("Database migration failed");
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // Close connection only if you're done with all migrations and other operations
            MySQLConnection.closeConnection();
        }
    }
}
