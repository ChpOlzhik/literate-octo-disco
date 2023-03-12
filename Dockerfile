#
#ARG DB_NAME
#ARG DB_URL
#ARG DB_USER
#ARG DB_PASSWORD
#
##
## Build stage
##
#FROM maven:3.6.0-jdk-11-slim AS build
#
#ENV DB_NAME=$DB_NAME
#ENV DB_URL=$DB_URL
#ENV DB_USER=$DB_USER
#ENV DB_PASSWORD=$DB_PASSWORD
#
#WORKDIR /home/app
#COPY . /home/app/
#RUN mvn clean install
#
##
## Package stage
##
#FROM openjdk:11-jre-slim
#ENV DB_NAME=$DB_NAME
#ENV DB_URL=$DB_URL
#ENV DB_USER=$DB_USER
#ENV DB_PASSWORD=$DB_PASSWORD
#COPY --from=build /home/app/diploma-backend/target/diploma-backend-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]

FROM openjdk:11-jre-slim
COPY ./diploma-backend/target/diploma-backend-0.0.1-SNAPSHOT.jar /home/app/demo.jar
ENTRYPOINT ["java","-jar","/home/app/demo.jar"]
