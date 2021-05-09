FROM openjdk:8-jdk-alpine
RUN mkdir /app
COPY target/inventory-service-0.1.0.jar /app
COPY UserItems.db /app
WORKDIR /app
EXPOSE 5000
CMD ["java", "-jar", "inventory-service-0.1.0.jar"]
