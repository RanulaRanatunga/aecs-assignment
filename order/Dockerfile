FROM openjdk:11
EXPOSE 8090
ADD target/order-docker.jar order-docker.jar
ENTRYPOINT ["java" , "-jar" ,"/order-docker.jar"]
