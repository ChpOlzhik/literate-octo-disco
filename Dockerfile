FROM openjdk:11-jre-slim
ENV GOOGLE_APPLICATION_CREDENTIALS=/home/app/almaty_ustazy_backend/storage-credientials.json
COPY ./storage-credientials.json /home/app/almaty_ustazy_backend/storage-credientials.json
COPY ./diploma-backend/target/diploma-backend-0.0.1-SNAPSHOT.jar /home/app/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/app/demo.jar"]
