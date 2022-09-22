FROM openjdk:8
EXPOSE 8080
ADD ./dogBreeds-service.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
