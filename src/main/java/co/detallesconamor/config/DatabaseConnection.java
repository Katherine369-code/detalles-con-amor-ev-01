
package co.detallesconamor.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Centraliza la configuración y creación de conexiones JDBC
 * para la aplicación Detalles con Amor.
 */
public final class DatabaseConnection {

    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/detalles_con_amor"
            + "?serverTimezone=UTC";

    private static final String DEFAULT_USER = "detalles_app";

    /*
     * Esta es la contraseña de ejemplo utilizada anteriormente.
     * Reemplázala si configuraste una contraseña diferente en MySQL.
     */
    private static final String DEFAULT_PASSWORD =
            "MiClaveSegura2026!";

    /**
     * Constructor privado para evitar instancias de esta clase.
     */
    private DatabaseConnection() {
        // Clase de utilidad: no requiere instancias.
    }

    /**
     * Obtiene una conexión con la base de datos MySQL.
     *
     * Puedes sobrescribir la configuración mediante las variables
     * de entorno DB_URL, DB_USER y DB_PASSWORD.
     *
     * @return conexión activa con MySQL.
     * @throws SQLException si ocurre un error de conexión.
     */
    public static Connection getConnection() throws SQLException {

        String url = getEnvironmentValue(
                "DB_URL", DEFAULT_URL);

        String user = getEnvironmentValue(
                "DB_USER", DEFAULT_USER);

        String password = getEnvironmentValue(
                "DB_PASSWORD", DEFAULT_PASSWORD);

        return DriverManager.getConnection(
                url, user, password);
    }

    /**
     * Obtiene una variable de entorno o utiliza el valor
     * predeterminado si no existe o está vacío.
     *
     * @param key nombre de la variable de entorno.
     * @param defaultValue valor predeterminado.
     * @return valor de configuración.
     */
    private static String getEnvironmentValue(
            String key, String defaultValue) {

        String value = System.getenv(key);

        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        return value;
    }

    /**
     * Comprueba si la aplicación puede conectarse con MySQL.
     *
     * @return true si la conexión funciona; false en caso contrario.
     */
    public static boolean testConnection() {

        try (Connection connection = getConnection()) {
            return connection.isValid(5);

        } catch (SQLException e) {
            System.err.println(
                    "Error de conexión con MySQL: "
                    + e.getMessage());

            return false;
        }
    }
}
