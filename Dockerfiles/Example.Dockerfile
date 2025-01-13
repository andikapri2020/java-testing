FROM vegardit/graalvm-maven:latest-java17 AS build
COPY ./src /buildDir/src
COPY ./pom.xml /buildDir
WORKDIR /buildDir
RUN mvn clean package -Pnative && chmod +x /buildDir/target/example

FROM debian:bookworm-slim
COPY --from=build /buildDir/target/example /app/example
ENTRYPOINT /app/example