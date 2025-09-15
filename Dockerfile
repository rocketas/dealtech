FROM eclipse-temurin:21-jre-alpine

EXPOSE 8080

WORKDIR /app

COPY app/build/libs/app.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-jar","/app/spring-boot-application.jar"]