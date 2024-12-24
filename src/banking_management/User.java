package banking_management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class User {
    private String full_name;
    private String email;
    private String password;
    Connection connection;
    Scanner scanner;
    public User(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public  void register_new_user(){
        try {
            scanner.nextLine();
            System.out.print("Enter Full Name: ");
            full_name = scanner.nextLine();
            System.out.print("Enter Email: ");
            email = scanner.nextLine();
            System.out.print("Enter Password: ");
            password = scanner.nextLine();

//            CHECK EXIST USER
            if(is_user_exist(email)) {
                System.out.println("User with this email already exists");
            }else {
                String sql = "INSERT INTO users(full_name, email, password) VALUES(?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, full_name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
                int row = preparedStatement.executeUpdate();
                if (row > 0) {
                    System.out.println("User registered successfully");
                }else {
                    System.out.println("Username already exists");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


//    USER LOGIN
      public String login_user (){
        try {
            scanner.nextLine();
            System.out.println("Enter email: ");
            email = scanner.nextLine();
            System.out.println("Enter password: ");
            password = scanner.nextLine();
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                String password = rs.getString("password");
                String userEmail = rs.getString("email");
                if (password.equals(password)) {
                    System.out.println("User logged in successfully");
                    return  userEmail;
                }else {
                    System.out.println("Wrong password");
                    return  null;
                }
            }else {
                System.out.println("User not found with this email");
                return null;
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
      }

      public  boolean is_user_exist(String email) {
        try {
            String existQuery = "SELECT * FROM users WHERE email = ?";
            PreparedStatement existPreparedStatement = connection.prepareStatement(existQuery);
            existPreparedStatement.setString(1, email);
            ResultSet rs = existPreparedStatement.executeQuery();
            if(rs.next()) {
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
      }

}
