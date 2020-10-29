FROM openjdk:11
EXPOSE 8010
ADD target/delivery-docker.jar delivery-docker.jar
ENTRYPOINT ["java" , "-jar" ,"/delivery-docker.jar"]
