# Detalles con Amor — Módulo CRUD de productos con JDBC

Proyecto académico Java + Maven + JDBC + MySQL para gestionar el catálogo de productos de la tienda virtual **Detalles con Amor**.

## Requisitos
- JDK 17 o superior
- Maven 3.8+
- MySQL 8+
- Git (para versionamiento)

## 1. Crear la base de datos
Ejecuta el archivo `database/schema.sql` desde MySQL Workbench o la consola de MySQL.

## 2. Configurar la conexión
Configura estas variables de entorno antes de ejecutar el programa:

- `DB_URL` (opcional; valor predeterminado: `jdbc:mysql://localhost:3306/detalles_con_amor?serverTimezone=UTC`)
- `DB_USER` (opcional; valor predeterminado: `root`)
- `DB_PASSWORD` (recomendado; contraseña de tu usuario MySQL)

En Windows PowerShell, por ejemplo:
```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/detalles_con_amor?serverTimezone=UTC"
$env:DB_USER="root"
$env:DB_PASSWORD="TU_CONTRASENA"
mvn clean compile
mvn exec:java -Dexec.mainClass="co.detallesconamor.Main"
```

Si no tienes configurado el plugin de ejecución de Maven, usa:
```powershell
mvn clean package
java -cp "target/gestion-productos-1.0.0.jar;RUTA_A_MYSQL_CONNECTOR_JAR" co.detallesconamor.Main
```
La forma más sencilla para ejecutar el proyecto es abrirlo en IntelliJ IDEA o Eclipse como proyecto Maven y ejecutar `co.detallesconamor.Main`.

## Funcionalidades CRUD
- **Crear:** registrar un producto.
- **Consultar:** listar productos y buscar por ID.
- **Actualizar:** modificar nombre, descripción, precio, stock y categoría.
- **Eliminar:** eliminar un producto por ID con confirmación.

## Estructura
```text
src/main/java/co/detallesconamor/
  Main.java
  config/DatabaseConnection.java
  model/Producto.java
  dao/ProductoDAO.java
database/schema.sql
```

## Buenas prácticas
- Paquetes en minúscula.
- Clases en PascalCase.
- Variables y métodos en camelCase.
- Consultas parametrizadas con `PreparedStatement`.
- Cierre automático de recursos con `try-with-resources`.
- Credenciales por variables de entorno; no guardar contraseñas reales en el repositorio.

## Versionamiento con Git
Desde la carpeta del proyecto:
```bash
git init
git add .
git commit -m "Inicializa módulo CRUD de productos con JDBC"
```
Crea un repositorio vacío en GitHub o GitLab y luego ejecuta los comandos que la plataforma indique, por ejemplo:
```bash
git branch -M main
git remote add origin https://github.com/USUARIO/REPOSITORIO.git
git push -u origin main
```
Reemplaza la URL de ejemplo por la URL real de tu repositorio.

## Evidencia del repositorio
La URL real se debe copiar en `REPOSITORIO.txt` después de crear y publicar el repositorio. No se incluye una URL ficticia.

## Nota
Este módulo es una aplicación de consola para demostrar las operaciones CRUD con JDBC. Puede integrarse posteriormente con la interfaz web del proyecto.
