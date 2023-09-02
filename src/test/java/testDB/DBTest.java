package testDB;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DBTest {
    private static String url = "jdbc:h2:tcp://localhost:9092/mem:testdb";
    private static String user = "user";
    private static String password = "pass";

    private static DataSource getH2DataSource() throws SQLException {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Test
    void test() throws SQLException {
        Connection conn = getH2DataSource().getConnection();
        try (Statement statement = conn.createStatement()) {
            int addPotato = statement.executeUpdate("INSERT INTO food VALUES (5, 'Картофель', 'Vegetable', false)");
        }
        try (PreparedStatement searchPotato = conn
                .prepareStatement("select * from food where food_name = 'Картофель'")) {
            try (ResultSet rs1 = searchPotato.executeQuery()) {
                while (rs1.next()) {
                    assertEquals("5", rs1.getString("FOOD_ID"));
                    assertEquals("Картофель", rs1.getString("FOOD_NAME"));
                    assertEquals("Vegetable", rs1.getString("FOOD_TYPE"));
                    assertEquals("0", rs1.getString("FOOD_EXOTIC"));
                }
            }
        }
        try (Statement statement = conn.createStatement()) {
            int delPotato = statement.executeUpdate("DELETE FROM food WHERE food_id = 5");
        }
        try (PreparedStatement searchPotatoNotExists = conn
                .prepareStatement("select * from food where food_id = 5")) {
            try (ResultSet rs2 = searchPotatoNotExists.executeQuery()) {
                while (rs2.next()) {
                    assertNull(rs2.getString("FOOD_ID"));
                }
            }
        }
        conn.close();
    }
}