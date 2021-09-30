FROM openjdk

WORKDIR /app

COPY target/HomeSecurity-1.0-SNAPSHOT.jar /app/HomeSecurity.jar

ENTRYPOINT ["java", "-jar", "HomeSecurity.jar"]