package com.rusile.web_lab3.repo;

import com.rusile.web_lab3.beans.HitCheck;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ApplicationScoped
public class HitCheckRepoImpl implements HitCheckRepo {
    //private final String dbUrl = "jdbc:postgresql://pg:5432/studs";
//    private final String user = "s335091";
//    private final String pass = "hf34JQYxZj7ZtojH";
    private final String dbUrl ="jdbc:postgresql://localhost:5432/disk_api_db?useSSL=false&amp&serverTimezone=UTC";
    private final String user = "postgres";
    private final String pass = "postgres";
    public HitCheckRepoImpl() {
        try {
            Class.forName("org.postgresql.Driver");
            initializeDB();
        } catch (ClassNotFoundException e) {
            System.out.println("No DB driver!");
            System.exit(1);
        }
    }

    @Override
    public void deleteAll(String id) {
        try (Connection connection = DriverManager.getConnection(dbUrl, user, pass)) {

            Statement statement = connection.createStatement();

            statement.execute("DELETE FROM user_table WHERE " +
                    "session_id = " + "'" + id + "'" +
                    ";");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(String id, HitCheck hitCheck) {
        try (Connection connection = DriverManager.getConnection(dbUrl, user, pass)) {

            Statement statement = connection.createStatement();

            statement.execute("INSERT INTO user_table(x, y, r, inArea, script_time, time, session_id) VALUES" +
                    "(" +
                    hitCheck.getX() + "," +
                    hitCheck.getY() + "," +
                    hitCheck.getR() + "," +
                    hitCheck.isInArea() + "," +
                    hitCheck.getExecutionTime() + "," +
                    "TO_TIMESTAMP('" + hitCheck.getStartTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")) + "', 'MM/dd/yyyy HH24:mi:ss')" + "," +
                    "'" + id + "'" +
                    ");");

        } catch (SQLException e) {
            System.out.println(hitCheck.getStartTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")));
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<HitCheck> getAllResults(String id) {
        List<HitCheck> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbUrl, user, pass)) {


            String selectCollectionQuery = "SELECT * FROM user_table where session_id = " + "'" + id + "'" + ";";
            Statement statement = connection.createStatement();
            ResultSet collectionSet = statement.executeQuery(selectCollectionQuery);
            while (collectionSet.next()) {
                HitCheck hitCheck = new HitCheck();
                hitCheck.setX(collectionSet.getDouble("x"));
                hitCheck.setY(collectionSet.getDouble("y"));
                hitCheck.setR(collectionSet.getDouble("r"));
                hitCheck.setInArea(collectionSet.getBoolean("inArea"));
                hitCheck.setExecutionTime(collectionSet.getLong("script_time"));
                hitCheck.setStartTime(collectionSet.getTimestamp("time").toInstant().atZone(ZoneOffset.UTC));

                result.add(hitCheck);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private void initializeDB() {

        try (Connection connection = DriverManager.getConnection(dbUrl, user, pass)) {

            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS user_table" +
                    "(" +
                    "id serial PRIMARY KEY," +
                    "x double precision," +
                    "y double precision," +
                    "r double precision," +
                    "inArea boolean," +
                    "script_time bigint," +
                    "time timestamp," +
                    "session_id varchar(500)" +
                    ");"
            );
        } catch (SQLException e) {
            System.out.println("Error occurred during initializing tables!" + e.getMessage());
            System.exit(1);
        }
    }
}
