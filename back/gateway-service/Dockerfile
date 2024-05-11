FROM openjdk:17

VOLUME /tmp
COPY ./target/*.jar gateway.jar
ENTRYPOINT ["java", "-jar", "gateway.jar"]