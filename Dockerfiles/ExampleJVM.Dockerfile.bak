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

ENV APM_SERVICE_NAME=${APM_SERVICE_NAME}
ENV APM_URL=${APM_URL}
ENV APM_SECRET_TOKEN=${APM_SECRET_TOKEN}
ENV APM_ENVIRONMENT=${APM_ENVIRONMENT}


#ENTRYPOINT java -jar /app/example.jar
#ENTRYPOINT ["java","-javaagent:/usr/local/newrelic/newrelic.jar","-jar","/app/example.jar"]
ENTRYPOINT ["java","-javaagent:/usr/local/apm/elastic-apm-agent.jar","-Delastic.apm.service_name=$APM_SERVICE_NAME","-Delastic.apm.server_urls=$APM_URL","-Delastic.apm.secret_token=$APM_SECRET_TOKEN","-Delastic.apm.environment=$APM_ENVIRONMENT","-jar","/app/example.jar"]
