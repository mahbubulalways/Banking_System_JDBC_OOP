package banking_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AccountManager {

    private Scanner scanner;
    private Connection connection;

    public AccountManager(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;

    }

    // debit balance 
    public void debit_balance(String email) {
        System.out.print("Enter Your Amount: ");
        double amount = scanner.nextDouble();
        System.out.print("Enter Your 4 Digit Pin: ");
        int pin = scanner.nextInt();
        String query = "UPDATE Accounts SET balance = balance - ? WHERE email = ?";

        try {
            if (pin_matcher(email, pin)) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDouble(1, amount);
                preparedStatement.setString(2, email);
                int updateRow = preparedStatement.executeUpdate();
                if (updateRow > 0) {
                    System.out.println("Balance Debit Successfully.");
                } else {
                    System.out.println("Something Went Wrong.");
                }

            } else {
                System.out.println("User Not Exist.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // debit balance 
    public void credit_balance(String email) {
        System.out.print("Enter Your Amount: ");
        double amount = scanner.nextDouble();
        System.out.print("Enter Your 4 Digit Pin: ");
        int pin = scanner.nextInt();
        String query = "UPDATE Accounts SET balance = balance + ? WHERE email = ?";

        try {
            if (pin_matcher(email, pin)) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDouble(1, amount);
                preparedStatement.setString(2, email);
                int updateRow = preparedStatement.executeUpdate();
                if (updateRow > 0) {
                    System.out.println("Balance Credit Successfully.");
                } else {
                    System.out.println("Something Went Wrong.");
                }

            } else {
                System.out.println("User Not Exist.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //  CHECK BALANCE
    public void check_balance(String email) {
        try {
            System.out.print("Enter Your 4 Digit Pin: ");
            int pin = scanner.nextInt();
            if (pin_matcher(email, pin)) {
                String findAccount = "SELECT balance FROM Accounts WHERE email = ?";
                PreparedStatement findUserStatement = connection.prepareStatement(findAccount);
                findUserStatement.setString(1, email);
                ResultSet user = findUserStatement.executeQuery();
                while (user.next()) {
                    double balance = user.getDouble("balance");
                    System.out.println("Your Current Balance:  " + balance);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // GET ACCOUNT NUMBER
    public void get_account_number(String email) {
        try {
            System.out.println("Enter 4 Digit pin:");
            int pin = scanner.nextInt();

            if (pin_matcher(email, pin)) {
                String query = "SELECT account_number FROM Accounts WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    long account_number = resultSet.getLong("account_number");
                    System.out.println("Account Number " + account_number);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //  PIN MATCHER
    public boolean pin_matcher(String email, int pin) {
        try {
            String findAccount = "SELECT * FROM Accounts WHERE email = ?";
            PreparedStatement findUserStatement = connection.prepareStatement(findAccount);
            findUserStatement.setString(1, email);
            ResultSet user = findUserStatement.executeQuery();
            while (user.next()) {
                int getPin = user.getInt("security_pin");
                if (getPin == pin) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
