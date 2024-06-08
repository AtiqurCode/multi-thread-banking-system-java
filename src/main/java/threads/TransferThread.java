package threads;

import transaction.Transaction;
import java.sql.SQLException;

public class TransferThread extends Thread {
    private int fromAccountId;
    private int toAccountId;
    private double amount;

    public TransferThread(int fromAccountId, int toAccountId, double amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            Transaction.transfer(fromAccountId, toAccountId, amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
