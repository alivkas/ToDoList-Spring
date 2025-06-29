FROM openjdk:21-jdk-slim
LABEL authors="alivka"

WORKDIR /to-do-list
COPY target/todolist-spring-0.0.1-SNAPSHOT.jar to-do-list.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "to-do-list.jar"]