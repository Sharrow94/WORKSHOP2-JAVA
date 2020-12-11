import java.sql.*;

public class DBUtil {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/workshop2?useSSL=" +
            "false&characterEncoding=utf8";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "coderslab";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(
                DB_URL, DB_USER, DB_PASS
        );
    }

    public static void insert(Connection conn, String query, String... params) {
        try ( PreparedStatement statement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

