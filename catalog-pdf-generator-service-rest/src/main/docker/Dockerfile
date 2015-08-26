FROM java:8
ADD catalog-pdf-generator-service.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]