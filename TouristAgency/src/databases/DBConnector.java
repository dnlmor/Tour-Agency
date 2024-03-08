package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/javaclass";
    private static final String USER = "root";
    private static final String PASSWORD = "ahgase07";

    private static Connection connection;

    public static Connection connect() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
