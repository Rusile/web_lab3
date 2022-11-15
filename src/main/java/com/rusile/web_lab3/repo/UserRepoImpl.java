package com.rusile.web_lab3.repo;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.util.UUID;

@ManagedBean
@ApplicationScoped
public class UserRepoImpl implements UserRepo {
    private final String dbUrl ="jdbc:postgresql://localhost:5432/disk_api_db?useSSL=false&amp&serverTimezone=UTC";
    private final String user = "postgres";
    private final String pass = "postgres";

    public UserRepoImpl() {
        try {
            Class.forName("org.postgresql.Driver");
            initializeDB();
        } catch (ClassNotFoundException e) {
            System.out.println("No DB driver!");
            System.exit(1);
        }
    }

    private void initializeDB() {
        try (Connection connection = DriverManager.getConnection(dbUrl, user, pass)) {

            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS users" +
                    "(" +
                    "uid varchar(255) NOT NULL ," +
                    "uname VARCHAR(60) NOT NULL, " +
                    "password VARCHAR(60) NOT NULL, " +
                    "PRIMARY KEY(uid)" +
                    ");"
            );
        } catch (SQLException e) {
            System.out.println("Error occurred during initializing tables!" + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public boolean addUser(String login, String password) {
        if (isLoginExists(login))
            return false;
        try (Connection connection = DriverManager.getConnection(dbUrl, user, pass)) {

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO users(uid, uname, password) VALUES (?, ?, ?);");

            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, password);

            preparedStatement.execute();

            return true;
        } catch (SQLException e) {
            System.out.println("Error occurred during adding user!" + e.getMessage());
            System.exit(1);
        }

        return false;
    }

    @Override
    public boolean isUserRegistered(String login, String password) {
        try (Connection connection = DriverManager.getConnection(dbUrl, user, pass)) {

            PreparedStatement ps = connection.prepareStatement("Select uname, password from Users where uname = ? and password = ?;");
            ps.setString(1, login);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error occurred during getting user!" + e.getMessage());
            System.exit(1);
        }
        return false;
    }

    public boolean isLoginExists(String login) {
        try (Connection connection = DriverManager.getConnection(dbUrl, user, pass)) {

            PreparedStatement ps = connection.prepareStatement("Select uname, password from Users where uname = ?;");
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error occurred during getting login!" + e.getMessage());
            System.exit(1);
        }
        return false;
    }
}
