import account.Account;
import transaction.Transaction;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
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
                    System.out.println("Thank you for having with us.");
                    System.out.println("Exiting...");
                    scanner.close();
                    return; // exit the system after account create

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
