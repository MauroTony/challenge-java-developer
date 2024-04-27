FROM openjdk:21-jdk-slim

WORKDIR /app

COPY mvnw pom.xml mvnw.cmd ./
COPY .mvn .mvn

RUN  apt-get update && apt-get install dos2unix
RUN sed -i 's/\r$//' mvnw
RUN /bin/sh mvnw dependency:resolve
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw package -DskipTests

EXPOSE 5000

CMD ["./mvnw", "spring-boot:run"]