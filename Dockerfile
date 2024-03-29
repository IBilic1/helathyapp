FROM gradle:8.3.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle -x test build --no-daemon

FROM openjdk:17-jdk
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java","-Dskip.tests", "-Dspring.profiles.active=prod", "-jar","/app/spring-boot-application.jar"]