# Sistema de Gestión de Reservas de Restaurante

### Alumno
Óscar Romera Rodríguez

---
 
# Descripción de la aplicación

Aplicación desarrollada en Java para la gestión de reservas de varios restaurantes utilizando:

- Jakarta Persistence (JPA)
- Hibernate ORM
- MySQL
- Maven
- Lombok

El proyecto permite gestionar:

- Restaurantes
- Mesas
- Clientes
- Reservas

---

# Tecnologías utilizadas

- Java 21
- Maven
- Hibernate ORM 7
- Jakarta Persistence API 3.2
- MySQL 8
- Lombok

---

# Estructura del proyecto

```text
proyecto-jpa-restaurantes/
└── src/
    └── main/
        ├── resources/
        │   └── META-INF/
        │       └── persistence.xml
        └── java/
            ├── entities/
            ├── repositories/
            ├── services/
            ├── utils/
            └── Main.java
```

# Configuración de MySQL

## 1. Crear la base de datos

```sql
CREATE DATABASE restaurantes_db;
```
---

## 2. Configurar persistence.xml

Ruta:

```text
src/main/resources/META-INF/persistence.xml
```

Modificar:

```xml
<property name="jakarta.persistence.jdbc.user" value="TU_USUARIO"/>
<property name="jakarta.persistence.jdbc.password" value="TU_PASSWORD"/>
```

Ejemplo:

```xml
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value="1234"/>
```