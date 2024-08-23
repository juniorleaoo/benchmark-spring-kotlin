FROM openjdk:11-jdk

COPY ./user-11-webflux/build/libs/user-11-webflux-0.0.1.jar app.jar

ENTRYPOINT ["sh", "-c", "java  -jar app.jar"]