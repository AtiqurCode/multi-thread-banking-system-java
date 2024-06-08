package threads;

import transaction.Transaction;

import java.sql.SQLException;

public class DepositThread extends Thread {
    private int accountId;
    private double amount;

    public DepositThread(int accountId, double amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            Transaction.deposit(accountId, amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
