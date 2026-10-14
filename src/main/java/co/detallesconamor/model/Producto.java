package co.detallesconamor.model;

import java.math.BigDecimal;

/**
 * Representa un producto del catálogo de Detalles con Amor.
 */
public class Producto {
    private long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int stock;
    private String categoria;

    public Producto(long id, String nombre, String descripcion,
                    BigDecimal precio, int stock, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    public Producto(String nombre, String descripcion, BigDecimal precio,
                    int stock, String categoria) {
        this(0, nombre, descripcion, precio, stock, categoria);
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Producto: %s | Categoría: %s | Precio: $%s | Stock: %d | Descripción: %s",
                id, nombre, categoria, precio.toPlainString(), stock,
                descripcion == null ? "" : descripcion);
    }
}
