FROM gradle:8.8.0-jdk22 AS build
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle clean bootJar --no-daemon --stacktrace
FROM openjdk:22-jdk
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/spring-boot-application.jar"]
