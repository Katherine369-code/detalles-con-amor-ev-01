package co.detallesconamor.dao;

import co.detallesconamor.config.DatabaseConnection;
import co.detallesconamor.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Acceso a datos para las operaciones CRUD de productos mediante JDBC.
 */
public class ProductoDAO {

    public Producto crear(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, stock, categoria) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setBigDecimal(3, producto.getPrecio());
            statement.setInt(4, producto.getStock());
            statement.setString(5, producto.getCategoria());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    producto.setId(keys.getLong(1));
                }
            }
            return producto;
        }
    }

    public List<Producto> consultarTodos() throws SQLException {
        String sql = "SELECT id, nombre, descripcion, precio, stock, categoria "
                + "FROM productos ORDER BY id";
        List<Producto> productos = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                productos.add(mapearProducto(resultSet));
            }
        }
        return productos;
    }

    public Optional<Producto> consultarPorId(long id) throws SQLException {
        String sql = "SELECT id, nombre, descripcion, precio, stock, categoria "
                + "FROM productos WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapearProducto(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    public boolean actualizar(Producto producto) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, "
                + "stock = ?, categoria = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setBigDecimal(3, producto.getPrecio());
            statement.setInt(4, producto.getStock());
            statement.setString(5, producto.getCategoria());
            statement.setLong(6, producto.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean eliminar(long id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    private Producto mapearProducto(ResultSet resultSet) throws SQLException {
        return new Producto(
                resultSet.getLong("id"),
                resultSet.getString("nombre"),
                resultSet.getString("descripcion"),
                resultSet.getBigDecimal("precio"),
                resultSet.getInt("stock"),
                resultSet.getString("categoria"));
    }
}
