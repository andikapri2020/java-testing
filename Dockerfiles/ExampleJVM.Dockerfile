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

RUN mkdir -p /usr/local/newrelic
ADD ./newrelic/newrelic.jar /usr/local/newrelic/newrelic.jar
ADD ./newrelic/newrelic.yml /usr/local/newrelic/newrelic.yml

#ENTRYPOINT java -jar /app/example.jar
ENTRYPOINT ["java","-javaagent:/usr/local/newrelic/newrelic.jar","-jar","/app/example.jar"]
