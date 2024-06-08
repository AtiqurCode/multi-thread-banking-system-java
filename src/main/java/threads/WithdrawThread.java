package threads;

import transaction.Transaction;
import java.sql.SQLException;

public class WithdrawThread extends Thread {
    private int accountId;
    private double amount;

    public WithdrawThread(int accountId, double amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            Transaction.withdraw(accountId, amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
