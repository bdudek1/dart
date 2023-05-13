FROM amazoncorretto:17

ADD target/dart-0.0.1-SNAPSHOT.jar dart-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "dart-0.0.1-SNAPSHOT.jar"]