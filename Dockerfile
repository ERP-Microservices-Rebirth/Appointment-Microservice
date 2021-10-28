FROM java:8
EXPOSE 8088
ADD /target/Appointment-Microservice-0.0.1-SNAPSHOT.jar appointment-microservice.jar
ENTRYPOINT [ "java", "-jar", "appointment-microservice.jar" ]