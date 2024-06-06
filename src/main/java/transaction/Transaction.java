package transaction;

import account.Account;
import connection.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Transaction {
    public synchronized static void deposit(int accountId, double amount) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            String sql = "UPDATE Accounts SET balance = balance + ? WHERE account_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, accountId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                logTransaction(connection, accountId, "DEPOSIT", amount);
                connection.commit();
                System.out.println("Deposit successful.");
            } else {
                connection.rollback();
                System.out.println("Deposit failed.");
            }
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            // Do not close the connection; it should remain open for reuse
        }
    }

    // Method to perform a withdrawal
    public synchronized static void withdraw(int accountId, double amount) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Check balance
            double currentBalance = getBalance(connection, accountId);
            if (currentBalance < amount) {
                System.out.println("Insufficient funds.");
                return;
            }

            String sql = "UPDATE Accounts SET balance = balance - ? WHERE account_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, accountId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                logTransaction(connection, accountId, "WITHDRAWAL", amount);
                connection.commit();
                System.out.println("Withdrawal successful.");
            } else {
                connection.rollback();
                System.out.println("Withdrawal failed.");
            }
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            // Do not close the connection; it should remain open for reuse
        }
    }

    // Method to perform a transfer
    public synchronized static void transfer(int fromAccountId, int toAccountId, double amount) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = MySQLConnection.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Check balance
            double fromAccountBalance = getBalance(connection, fromAccountId);
            if (fromAccountBalance < amount) {
                System.out.println("Insufficient funds.");
                return;
            }

            // Deduct from sender
            String sqlDeduct = "UPDATE Accounts SET balance = balance - ? WHERE account_id = ?";
            preparedStatement = connection.prepareStatement(sqlDeduct);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, fromAccountId);
            preparedStatement.executeUpdate();

            // Add to receiver
            String sqlAdd = "UPDATE Accounts SET balance = balance + ? WHERE account_id = ?";
            preparedStatement = connection.prepareStatement(sqlAdd);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, toAccountId);
            preparedStatement.executeUpdate();

            logTransaction(connection, fromAccountId, "TRANSFER", amount);
            logTransaction(connection, toAccountId, "DEPOSIT", amount);

            connection.commit();
            System.out.println("Transfer successful.");
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            // Do not close the connection; it should remain open for reuse
        }
    }

    // Helper method to get account balance
    private static double getBalance(Connection connection, int accountId) throws SQLException {
        String sql = "SELECT balance FROM Accounts WHERE account_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("balance");
                } else {
                    throw new SQLException("Account not found.");
                }
            }
        }
    }

    // Helper method to log transactions
    private static void logTransaction(Connection connection, int accountId, String type, double amount) throws SQLException {
        String sql = "INSERT INTO Transactions (account_id, transaction_type, amount, created_at, updated_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setString(2, type);
            preparedStatement.setDouble(3, amount);
            preparedStatement.executeUpdate();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdrawal");
            System.out.println("4. Transfer");
            System.out.println("5. Check Account & Balance");
            System.out.println("6. Exit");
            System.out.print("Enter the option number: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter account holder name: ");
                    scanner.nextLine();  // Consume newline
                    String accountHolderName = scanner.nextLine();

                    System.out.print("Enter initial balance: ");
                    double balance = scanner.nextDouble();

                    Account.createAccount(accountHolderName, balance);
                    break;

                case 2:
                    System.out.print("Enter account number to deposit: ");
                    int depositAccount = scanner.nextInt();
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    try {
                        Transaction.deposit(depositAccount, depositAmount);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    System.out.print("Enter account number to withdraw: ");
                    int withdrawAccount = scanner.nextInt();
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    try {
                        Transaction.withdraw(withdrawAccount, withdrawAmount);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    System.out.print("Enter sender account number: ");
                    int fromAccount = scanner.nextInt();
                    System.out.print("Enter receiver account number: ");
                    int toAccount = scanner.nextInt();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    try {
                        Transaction.transfer(fromAccount, toAccount, transferAmount);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 5:
                    System.out.print("Enter account number to check: ");
                    int accountNumber = scanner.nextInt();
                    Account.accountCheck(accountNumber);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
