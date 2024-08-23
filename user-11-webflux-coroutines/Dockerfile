FROM openjdk:11-jdk

COPY ./user-11-webflux-coroutines/build/libs/user-11-webflux-coroutines-0.0.1.jar app.jar

ENTRYPOINT ["sh", "-c", "java -jar app.jar"]