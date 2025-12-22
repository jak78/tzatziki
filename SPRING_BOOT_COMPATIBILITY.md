# Spring Boot Compatibility Guide

This library supports both **Spring Boot 3.5.9** and **Spring Boot 4.0.1**.

## Default Configuration

By default, the library uses **Spring Boot 4.0.1** with **Spring Cloud 2025.1.0 (Oakwood)**.

## Building with Spring Boot 3.5.9

To build the library with Spring Boot 3.5.9, activate the `spring-boot-3` Maven profile:

```bash
mvn clean install -Pspring-boot-3
```

This will use:
- **Spring Boot 3.5.9**
- **Spring Cloud 2025.0.0 (Northfields)**

## Building with Spring Boot 4.0.1

To build with Spring Boot 4.0.1 (default), you can either:

```bash
# Build without specifying a profile (default)
mvn clean install

# Or explicitly activate the spring-boot-4 profile
mvn clean install -Pspring-boot-4
```

This will use:
- **Spring Boot 4.0.1**
- **Spring Cloud 2025.1.0 (Oakwood)**

## Requirements

Both Spring Boot versions require:
- **Java 17** or higher
- **Maven 3.6+**

## Module-Specific Notes

### tzatziki-spring-jpa

The `tzatziki-spring-jpa` module automatically detects the Hibernate version at runtime:
- **Hibernate 5** (Spring Boot 3.0-3.3) → uses `jackson-datatype-hibernate5-jakarta`
- **Hibernate 6** (Spring Boot 3.4+/4.x) → uses `jackson-datatype-hibernate6`

This ensures seamless compatibility across different Spring Boot versions without code changes.

### Dependencies

All Spring Boot and Spring Cloud dependencies are managed through the parent POM using Maven properties:
- `spring-boot.version` - The Spring Boot version to use
- `spring-cloud.version` - The Spring Cloud version to use

These properties are automatically set based on the active Maven profile.

## Testing Compatibility

To verify your application works with both Spring Boot versions:

1. Test with Spring Boot 3.5.9:
   ```bash
   mvn clean test -Pspring-boot-3
   ```

2. Test with Spring Boot 4.0.1:
   ```bash
   mvn clean test -Pspring-boot-4
   ```

## Version Matrix

| Spring Boot Version | Spring Cloud Version | Hibernate Version | Jackson Hibernate Module |
|---------------------|----------------------|-------------------|-------------------------|
| 3.5.9 | 2025.0.0 (Northfields) | 6.x | jackson-datatype-hibernate6 |
| 4.0.1 | 2025.1.0 (Oakwood) | 6.x | jackson-datatype-hibernate6 |

Both versions now use Hibernate 6 as Spring Boot 3.4+ upgraded to Hibernate 6.
