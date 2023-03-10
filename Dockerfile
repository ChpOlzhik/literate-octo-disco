FROM openjdk:11-jre-slim

WORKDIR /app

COPY diploma-backend/target /app/target
COPY storage-credientials.json /app/storage-credientials.json

ENV GOOGLE_APPLICATION_CREDENTIALS=/app/storage-credientials.json

CMD ["java", "-jar", "diploma-backend-0.0.1-SNAPHOT.jar"]