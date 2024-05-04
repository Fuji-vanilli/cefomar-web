FROM openjdk:17
VOLUME /tmp
COPY ./target/*.jar student.jar
ENTRYPOINT ["java", "-jar", "student.jar"]