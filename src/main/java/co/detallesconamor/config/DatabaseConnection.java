package co.detallesconamor.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Centraliza la configuración y creación de conexiones JDBC.
 */
public final class DatabaseConnection {
    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/detalles_con_amor?serverTimezone=UTC";
    private static final String DEFAULT_USER = "root";

    private DatabaseConnection() {
        // Evita crear instancias de esta clase de utilidad.
    }

    public static Connection getConnection() throws SQLException {
        String url = getEnvironmentValue("DB_URL", DEFAULT_URL);
        String user = getEnvironmentValue("DB_USER", DEFAULT_USER);
        String password = getEnvironmentValue("DB_PASSWORD", "");

        return DriverManager.getConnection(url, user, password);
    }

    private static String getEnvironmentValue(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null ? defaultValue : value;
    }
}
