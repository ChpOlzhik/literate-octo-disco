FROM openjdk:11
EXPOSE 8080
ADD . .
ENTRYPOINT ["java", "-jar", "diploma-backend/diploma-backend-0.0.1-SNAPSHOT.jar.jar"]