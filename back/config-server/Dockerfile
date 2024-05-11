FROM openjdk:17
VOLUME /tmp
COPY ./target/*.jar config.jar
ENTRYPOINT ["java", "-jar", "config.jar"]