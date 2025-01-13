FROM eclipse-temurin:17 AS build
COPY ./src /buildDir/src
COPY ./pom.xml /buildDir
COPY ./.mvn /buildDir/.mvn
COPY ./mvnw /buildDir
WORKDIR /buildDir 
RUN sed -i 's/\r$//' mvnw
RUN chmod +x mvnw
RUN ./mvnw clean package

FROM eclipse-temurin:17-alpine
COPY --from=build /buildDir/target/example.jar /app/example.jar
ENTRYPOINT java -jar /app/example.jar