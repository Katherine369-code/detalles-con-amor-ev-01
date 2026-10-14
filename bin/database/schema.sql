CREATE DATABASE IF NOT EXISTS detalles_con_amor
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE detalles_con_amor;

CREATE TABLE IF NOT EXISTS productos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(120) NOT NULL,
    descripcion VARCHAR(500),
    precio DECIMAL(12, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    categoria VARCHAR(80) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_productos_precio CHECK (precio >= 0),
    CONSTRAINT chk_productos_stock CHECK (stock >= 0)
);

INSERT INTO productos (nombre, descripcion, precio, stock, categoria)
SELECT 'Desayuno sorpresa clásico', 'Desayuno para una ocasión especial', 45000.00, 10, 'Desayunos'
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE nombre = 'Desayuno sorpresa clásico');
