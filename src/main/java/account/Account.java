package account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.MySQLConnection;


public class Account {
    public static void createAccount(String accountHolderName, double balance) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = MySQLConnection.getConnection();

            String sql = "INSERT INTO accounts (account_holder_name, balance) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, accountHolderName);
            preparedStatement.setDouble(2, balance);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int accountId = resultSet.getInt(1);
                    System.out.println("Account Created successfully. Account ID: " + accountId);
                }
            } else {
                System.out.println("Sorry! Failed to create the account.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // Do not close the connection; it should remain open for reuse
        }
    }

    public static void accountCheck(int accountNumber) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = MySQLConnection.getConnection();

            String sql = "SELECT account_holder_name, balance FROM Accounts WHERE account_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountNumber);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String accountHolderName = resultSet.getString("account_holder_name");
                double balance = resultSet.getDouble("balance");
                System.out.println("Account Holder Name: " + accountHolderName);
                System.out.println("Balance: " + balance);
            } else {
                System.out.println("No account found with account number: " + accountNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // Do not close the connection; it should remain open for reuse
        }
    }
}
