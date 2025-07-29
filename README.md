# README - Sistema de Gestión de Inventarios (Backend) 🚀

```txt
▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
█                                       █
█  🛠  GESTIÓN DE INVENTARIOS - BACKEND  █
█  ✨  Diseño Profesional & Buenas Prácticas  █
█  🚀  Spring Boot | JPA | REST API      █
█                                       █
▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀

```

## 🌟 Características Destacadas

```diff
+ ARQUITECTURA LIMPIA Y MODULAR
+ IMPLEMENTACIÓN DE MEJORES PRÁCTICAS
+ MANEJO DE EXCEPCIONES CENTRALIZADO
+ VALIDACIONES ROBUSTAS
+ DTOs PARA TRANSFERENCIA SEGURA DE DATOS
+ DOCUMENTACIÓN INTERNA
```

---

## 📂 Estructura del Proyecto (Arquitectura por Capas)

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── gestionInventarios/
│   │           ├── controller/        👉 Capa de controladores REST
│   │           ├── dto/               👉 Objetos de Transferencia de Datos (DTOs)
│   │           ├── entity/            👉 Entidades de persistencia (JPA)
│   │           ├── repository/        👉 Repositorios (Spring Data JPA)
│   │           ├── service/           👉 Lógica de negocio
│   │           │   └── impl/          👉 Implementaciones de servicios
│   │           └── util/              👉 Utilidades globales
│   └── resources/
│       ├── import.sql                 👉 Datos iniciales para pruebas
│       └── application.properties     👉 Configuración
```

---

## 🏆 Buenas Prácticas Implementadas

### 1. Patrón DTO (Data Transfer Object)
- **Beneficio**: Evita exponer la estructura interna de las entidades
- **Ejemplo**: `ProductoDto` para transferencia segura de datos

### 2. Validación de Entradas
```java
@PostMapping
public ResponseEntity<?> crearProducto(
    @Valid @RequestBody ProductoDto productoDto) { ... }
```
- Uso de anotaciones `@Valid` y `@NotNull` en DTOs

### 3. Manejo Centralizado de Excepciones
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(...) { ... }
}
```

### 4. Separación Clara de Responsabilidades
- Controllers → Manejo de requests/responses
- Services → Lógica de negocio
- Repositories → Acceso a datos

### 5. Transacciones
```java
@Transactional
public void realizarEntradaInventario(...) { ... }
```

### 6. Uso de Lombok
- Reduce código boilerplate con `@Data`, `@NoArgsConstructor`, etc.

---

## 🔧 Tecnologías Utilizadas

```
- Java 17
- Spring Boot 3.x
- Spring Data JPA (Hibernate)
- H2 Database (para desarrollo)
- Lombok
- Maven
- Jakarta Validation
```

---

## 🚀 Cómo Ejecutar

1. Clonar repositorio:
```bash
git clone https://github.com/franveggiani/gestionInventariosBE.git
```

2. Ejecutar con Maven:
```bash
mvn spring-boot:run
```

3. Acceder a la consola H2 (desarrollo):
```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: [vacío]
```

---

## 📚 Documentación de Endpoints

### Productos
- `GET /api/productos` → Listar todos los productos
- `GET /api/productos/{id}` → Obtener producto por ID
- `POST /api/productos` → Crear nuevo producto
- `PUT /api/productos/{id}` → Actualizar producto
- `DELETE /api/productos/{id}` → Eliminar producto

### Movimientos de Inventario
- `POST /api/movimientos/entrada` → Registrar entrada de stock
- `POST /api/movimientos/salida` → Registrar salida de stock

---

## Ejemplo de Código (Manejo Elegante de Excepciones)

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, String>> handleValidationExceptions(
    MethodArgumentNotValidException ex) {
    
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
    });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
}
```

---

## 🤝 Contribuir

¡Se aceptan contribuciones! Por favor:
1. Haz un fork del proyecto
2. Crea una rama (`git checkout -b feature/nueva-funcionalidad`)
3. Realiza tus cambios
4. Haz push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

---

## 📄 Licencia
Este proyecto está bajo la [Licencia MIT](LICENSE).

---
▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀
```

> **Nota**: Este README destaca las mejores prácticas y arquitectura limpia del proyecto.  
> Para detalles técnicos completos, revisa el código fuente ✨
```
