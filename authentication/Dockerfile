FROM openjdk:11
EXPOSE 8050
ADD target/authentication-docker.jar authentication-docker.jar
ENTRYPOINT ["java" , "-jar" ,"/authentication-docker.jar"]
