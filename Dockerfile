FROM openjdk:17
ADD target/movie-ticket-booking-application-0.0.1-SNAPSHOT.jar backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "backend.jar"]
