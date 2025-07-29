# README - Sistema de Gestión de Inventarios (Backend) 🚀

```txt
▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
█                                       █
█  🏭 GESTIÓN DE INVENTARIOS - BACKEND   █
█  🔥 Patrones de Diseño & Spring Boot   █
█  🚀 Java 17 | JPA | REST API | H2      █
█                                       █
▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀
```

## Características Destacadas

```
+ ARQUITECTURA POR CAPAS CON PATRONES DE DISEÑO
+ STRATEGY PARA MOVIMIENTOS DE INVENTARIO
+ FACTORY PARA CREACIÓN DE OBJETOS
+ DTOs PARA TRANSFERENCIA SEGURA
+ ENUMS PARA TIPOS ESTÁTICOS
+ VALIDACIONES ROBUSTAS
```

---

## 📂 Estructura del Proyecto
```
src/main/java/com/gestionInventarios/
├── controller/           👉 Controladores REST (Manejo de peticiones HTTP)
├── DTOs/                 👉 Objetos de Transferencia de Datos (Seguridad)
├── entities/             👉 Entidades de Persistencia (Mapeo JPA)
├── enums/                👉 Tipos Enumerados (Estados y Tipos)
├── factory/              👉 Patrón Factory (Creación de objetos)
├── repositories/         👉 Acceso a Datos (Spring Data JPA)
├── services/             👉 Lógica de Negocio (Implementaciones)
├── strategy/             👉 Patrón Strategy (Movimientos de inventario)
└── GestioninvApplication.java 👉 Punto de inicio de la aplicación
```

---

## 🏆 Patrones de Diseño Implementados

1. Patrón Strategy (Movimientos de Inventario)
2. Patrón Factory (Creación de Movimientos)
3. Patrón DTO (Data Transfer Object) para no exponer entidades
4. Uso de Enums para Tipos Estáticos

---

## 🛡️ Buenas Prácticas

### 1. Separación Clara de Responsabilidades
- **Controllers**: Manejo de requests/responses
- **Services**: Lógica de negocio (con Strategy y Factory)
- **Repositories**: Acceso a datos

### 2. Uso de DTOs
```java
// En DTOs/
public class ProductoDTO {
    private String nombre;
    private BigDecimal precio;
    // ... 
}
```
- **Beneficio**: Evita exposición directa de entidades

### 3. Documentación Interna
- Código bien comentado explicando patrones y lógica

---

## 🔧 Stack Tecnológico

```
- Java 17
- Spring Boot 3.x
- Spring Data JPA (Hibernate)
- H2 Database (embebida)
- Lombok (reducción de boilerplate)
- Maven (gestión de dependencias)
- Jakarta Bean Validation
```

---

## 🚀 Instalación y Ejecución

1. Clonar repositorio:
```bash
git clone https://github.com/franveggiani/gestionInventariosBE.git
```

2. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

3. Acceder a consola H2 (credenciales en application.properties):
```
http://localhost:8080/h2-console
```

---

