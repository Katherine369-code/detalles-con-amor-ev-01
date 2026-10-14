package co.detallesconamor;

import co.detallesconamor.dao.ProductoDAO;
import co.detallesconamor.model.Producto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Menú de consola para demostrar el CRUD de productos.
 */
public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final ProductoDAO PRODUCTO_DAO = new ProductoDAO();

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   DETALLES CON AMOR - GESTIÓN PRODUCTOS");
        System.out.println("==========================================");

        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            String opcion = SCANNER.nextLine().trim();

            try {
                switch (opcion) {
                    case "1" -> crearProducto();
                    case "2" -> consultarProductos();
                    case "3" -> consultarProductoPorId();
                    case "4" -> actualizarProducto();
                    case "5" -> eliminarProducto();
                    case "0" -> continuar = false;
                    default -> System.out.println("Opción no válida. Intenta nuevamente.");
                }
            } catch (SQLException exception) {
                System.err.println("Error de base de datos: " + exception.getMessage());
                System.err.println("Verifica que MySQL esté activo y que las credenciales sean correctas.");
            } catch (IllegalArgumentException exception) {
                System.err.println("Dato inválido: " + exception.getMessage());
            }
        }

        SCANNER.close();
        System.out.println("Aplicación finalizada.");
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Registrar producto");
        System.out.println("2. Consultar todos los productos");
        System.out.println("3. Buscar producto por ID");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Eliminar producto");
        System.out.println("0. Salir");
        System.out.print("Selecciona una opción: ");
    }

    private static void crearProducto() throws SQLException {
        Producto producto = solicitarDatosProducto();
        Producto creado = PRODUCTO_DAO.crear(producto);
        System.out.println("Producto registrado correctamente. ID asignado: " + creado.getId());
    }

    private static void consultarProductos() throws SQLException {
        List<Producto> productos = PRODUCTO_DAO.consultarTodos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        System.out.println("\n--- CATÁLOGO DE PRODUCTOS ---");
        productos.forEach(System.out::println);
    }

    private static void consultarProductoPorId() throws SQLException {
        long id = leerLong("Ingresa el ID del producto: ");
        Optional<Producto> producto = PRODUCTO_DAO.consultarPorId(id);
        System.out.println(producto.map(Producto::toString)
                .orElse("No se encontró un producto con ese ID."));
    }

    private static void actualizarProducto() throws SQLException {
        long id = leerLong("ID del producto que deseas actualizar: ");
        Optional<Producto> existente = PRODUCTO_DAO.consultarPorId(id);
        if (existente.isEmpty()) {
            System.out.println("No se encontró un producto con ese ID.");
            return;
        }

        System.out.println("Ingresa los nuevos datos del producto.");
        Producto producto = solicitarDatosProducto();
        producto.setId(id);

        if (PRODUCTO_DAO.actualizar(producto)) {
            System.out.println("Producto actualizado correctamente.");
        } else {
            System.out.println("No fue posible actualizar el producto.");
        }
    }

    private static void eliminarProducto() throws SQLException {
        long id = leerLong("ID del producto que deseas eliminar: ");
        Optional<Producto> producto = PRODUCTO_DAO.consultarPorId(id);
        if (producto.isEmpty()) {
            System.out.println("No se encontró un producto con ese ID.");
            return;
        }

        System.out.println("Se eliminará: " + producto.get().getNombre());
        System.out.print("¿Confirmas la eliminación? (S/N): ");
        String confirmacion = SCANNER.nextLine().trim();

        if (confirmacion.equalsIgnoreCase("S")) {
            boolean eliminado = PRODUCTO_DAO.eliminar(id);
            System.out.println(eliminado
                    ? "Producto eliminado correctamente."
                    : "No fue posible eliminar el producto.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private static Producto solicitarDatosProducto() {
        System.out.print("Nombre: ");
        String nombre = leerTextoObligatorio();
        System.out.print("Descripción (puede quedar vacía): ");
        String descripcion = SCANNER.nextLine().trim();
        BigDecimal precio = leerPrecio("Precio: ");
        int stock = leerEnteroNoNegativo("Stock: ");
        System.out.print("Categoría: ");
        String categoria = leerTextoObligatorio();

        return new Producto(nombre, descripcion, precio, stock, categoria);
    }

    private static String leerTextoObligatorio() {
        String valor = SCANNER.nextLine().trim();
        if (valor.isBlank()) {
            throw new IllegalArgumentException("Este campo es obligatorio.");
        }
        return valor;
    }

    private static BigDecimal leerPrecio(String mensaje) {
        System.out.print(mensaje);
        try {
            BigDecimal precio = new BigDecimal(SCANNER.nextLine().trim().replace(",", "."));
            if (precio.signum() < 0) {
                throw new IllegalArgumentException("El precio no puede ser negativo.");
            }
            return precio;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Ingresa un precio válido, por ejemplo 45000.00.");
        }
    }

    private static int leerEnteroNoNegativo(String mensaje) {
        System.out.print(mensaje);
        try {
            int valor = Integer.parseInt(SCANNER.nextLine().trim());
            if (valor < 0) {
                throw new IllegalArgumentException("El valor no puede ser negativo.");
            }
            return valor;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Ingresa un número entero válido.");
        }
    }

    private static long leerLong(String mensaje) {
        System.out.print(mensaje);
        try {
            long valor = Long.parseLong(SCANNER.nextLine().trim());
            if (valor <= 0) {
                throw new IllegalArgumentException("El ID debe ser mayor que cero.");
            }
            return valor;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Ingresa un ID numérico válido.");
        }
    }
}
