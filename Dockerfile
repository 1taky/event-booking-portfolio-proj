FROM eclipse-temurin:21-jre
WORKDIR /booking-app
COPY target/*.jar booking-app.jar
ENTRYPOINT ["java", "-jar", "booking-app.jar"]