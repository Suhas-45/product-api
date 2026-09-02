FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/product-api-*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]