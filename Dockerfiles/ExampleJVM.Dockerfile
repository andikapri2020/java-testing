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

#RUN mkdir -p /usr/local/newrelic
#ADD ./newrelic/newrelic.jar /usr/local/newrelic/newrelic.jar
#ADD ./newrelic/newrelic.yml /usr/local/newrelic/newrelic.yml

RUN mkdir -p /usr/local/apm
ADD ./apm/elastic-apm-agent.jar /usr/local/apm/elastic-apm-agent.jar
#ADD ./newrelic/newrelic.yml /usr/local/newrelic/newrelic.yml

#ENTRYPOINT java -jar /app/example.jar
#ENTRYPOINT ["java","-javaagent:/usr/local/newrelic/newrelic.jar","-jar","/app/example.jar"]
ENTRYPOINT ["java","-javaagent:/usr/local/apm/elastic-apm-agent.jar -Delastic.apm.service_name=java-testing-apm -Delastic.apm.server_urls=http://10.110.10.15:8200 -Delastic.apm.secret_token=secrettokengoeshere -Delastic.apm.environment=production","-jar","/app/example.jar"]
