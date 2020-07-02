FROM openjdk:14-alpine
COPY build/libs/micronaut-person-all.jar micronaut-person.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "micronaut-person.jar"]
