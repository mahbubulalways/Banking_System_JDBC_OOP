package banking_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Accounts {
    private String full_name;
    private String email;
    private int security_pin;
    private  long account_number;

//
    private Connection connection;
    private Scanner scanner;
    public Accounts(Connection connection, Scanner scanner) {
       this.connection = connection;
       this.scanner = scanner;
    }

//    METHODS


//    CREATE ACCOUNT

    public void create_bank_account(String exist_email) {
        try {

            String findUserQuery = "SELECT * FROM users WHERE email = ?";
            PreparedStatement findUser = connection.prepareStatement(findUserQuery);
            findUser.setString(1, exist_email);
            ResultSet rs = findUser.executeQuery();

while (rs.next()) {
    //  GET EMAIL AND NAME FROM USER TABLE
    String getEmail = rs.getString("email");
    String getFullName = rs.getString("full_name");

    //  GENERATE ACCOUNT NUMBER
    System.out.println("Your email and name already have to database");
    System.out.print("Enter 4 digit pin: ");
    security_pin = scanner.nextInt();
    account_number = generate_account_number();

    String query = "INSERT INTO Accounts(full_name,email,account_number,security_pin  ) VALUES (?,?,?,?)";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setString(1, getFullName);
    statement.setString(2, getEmail);
    statement.setLong(3, account_number);
    statement.setInt(4, security_pin);
    int result = statement.executeUpdate();
    if (result == 1) {
        System.out.println("Account created successfully");
    }else {
        System.out.println("Account creation failed");
    }

}
        } catch (Exception e) {
            System.out.println("Create Bank Account Exception => " + e.getMessage());
        }
    }




//    CHECK IF ACCOUNT IS EXITS WITH EMIL
    public  boolean is_account_exist(String email){
        try {
            String query = "SELECT * FROM accounts WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            System.out.println("Account Exists Exception => " + e.getMessage());
        }
        return false;
    }

//    GENERATE ACCOUNT NUMBER

    public  long generate_account_number() {
        try {
            String query = "SELECT account_number FROM accounts ORDER BY account_number DESC LIMIT 1";
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                long last_account_number= resultSet.getLong("account_number");
                return  last_account_number + 1;
            }else {
                return 1000;
            }

        } catch (Exception e) {
            System.out.println("Generate Account Number Exception => " + e.getMessage());
        }
         return 1000;

    }

}
