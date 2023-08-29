package testDB;

import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DBTest {
    private static String url = "jdbc:h2:tcp://localhost:9092/mem:testdb";
    private static String user = "user";
    private static String pass = "pass";

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(url,user,pass);
    }

    @Test
    void test() throws SQLException {
        Statement statement = getConn().createStatement();
        int addPotato = statement.executeUpdate("INSERT INTO food VALUES (5, 'Картофель', 'Vegetable', false)");

        PreparedStatement searchPotato = getConn()
                .prepareStatement("select * from food where food_name = 'Картофель'");
        try (ResultSet rs1 = searchPotato.executeQuery()) {
            while (rs1.next()) {
                assertEquals("5", rs1.getString("FOOD_ID"));
                assertEquals("Картофель", rs1.getString("FOOD_NAME"));
                assertEquals("Vegetable", rs1.getString("FOOD_TYPE"));
                assertEquals("0", rs1.getString("FOOD_EXOTIC"));
            }
        }
        int delPotato = statement.executeUpdate("DELETE FROM food WHERE food_id = 5");
        PreparedStatement searchPotatoNotExists = getConn()
                .prepareStatement("select * from food where food_id = 5");
        try (ResultSet rs2 = searchPotatoNotExists.executeQuery()) {
            while (rs2.next()) {
                assertNull(rs2.getString("FOOD_ID"));
            }
        }
        getConn().close();
    }
}