# ---- Etapa 1: compilar con Maven y JDK 21 ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -B dependency:go-offline
COPY src ./src
RUN mvn -q -B clean package -DskipTests

# ---- Etapa 2: ejecutar solo con JRE 21 ligero ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/aulalogica-1.0.0.jar app.jar
# Render gratis tiene 512 MB de RAM: limitamos la memoria de Java
ENV JAVA_TOOL_OPTIONS="-Xmx300m -XX:+UseSerialGC -XX:TieredStopAtLevel=1"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
