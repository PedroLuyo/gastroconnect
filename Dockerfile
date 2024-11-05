# Utiliza la imagen base de Maven para compilar el proyecto
FROM maven:3.8.4-openjdk-17-slim AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de configuración y la definición del proyecto
COPY pom.xml .
COPY src ./src

# Compila el proyecto y genera el archivo JAR
RUN mvn clean package -DskipTests

# Utiliza la imagen base de OpenJDK 17 slim para ejecutar la aplicación
FROM openjdk:17-jdk-alpine3.14

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado durante la compilación
COPY --from=build /app/target/ms-restaurants.jar .

# Exponer el puerto 8086 para que sea accesible desde fuera del contenedor
EXPOSE 8082

# Comando para ejecutar tu aplicación cuando se inicie el contenedor
CMD ["java", "-jar", "ms-restaurants"]
