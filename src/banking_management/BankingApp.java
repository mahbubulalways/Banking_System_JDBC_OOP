package banking_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class BankingApp {

    private final static String URL = "jdbc:mysql://localhost:3306/banking_system";
    private final static String USER = "root";
    private final static String PASSWORD = "Mahbubul123!";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Scanner scanner = new Scanner(System.in);
            User user = new User(connection, scanner);
            Accounts account = new Accounts(connection, scanner);
            AccountManager accountManager = new AccountManager(connection, scanner);

            String email;
            long account_number;
            while (true) {
                System.out.println();
                System.out.println("WELCOME TO BANK MANAGEMENT SYSTEM");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Press: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1: {
                        user.register_new_user();
                    }
                    case 2: {
                        email = user.login_user();
                        if (user.is_user_exist(email)) {
                            System.out.println();
                            if (!account.is_account_exist(email)) {
                                System.out.println("1.Open a new account");
                                System.out.println("2.Exits process");
                                System.out.print("Press: ");
                                int choice2 = scanner.nextInt();
                                switch (choice2) {
                                    case 1: {
                                        account.create_bank_account(email);
                                    }
                                }
                            } else {

                                System.out.println("1. Debit Money");
                                System.out.println("2. Credit Money");
                                System.out.println("3. Transfer Money");
                                System.out.println("4. Check Balance");
                                System.out.println("5. Account Number");
                                System.out.println("6. Exit Process");
                                System.out.print("Press: ");
                                int choice3 = scanner.nextInt();
                                switch (choice3) {
                                    case 1: {
                                        accountManager.debit_balance(email);
                                        break;
                                    }
                                    case 2: {
                                        accountManager.credit_balance(email);
                                        break;
                                    }
                                    case 4: {
                                        accountManager.check_balance(email);
                                    }
                                    case 5: {
                                        accountManager.get_account_number(email);
                                    }
                                }
                            }
                        }
                    }
                    case 3: {
                        System.out.println("THANK YOU FOR USING BANKING SYSTEM!!!");
                        System.out.println("Exiting System!");
                        return;
                    }
                    default: {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
