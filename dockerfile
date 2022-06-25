from gradle:7.4.2-jdk11-alpine as build

copy --chown=gradle:gradle . /home/gradle/src
workdir /home/gradle/src
run gradle bootJar -x test --no-daemon

from adoptopenjdk:11.0.11_9-jre-hotspot-focal

copy --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]