FROM gradle:8.4-jdk21 AS build
ARG GPR_USER
ARG GPR_KEY
WORKDIR /app
COPY . .
RUN echo "gpr.user=${GPR_USER}" > /root/.gradle/gradle.properties && \
    echo "gpr.key=${GPR_KEY}" >> /root/.gradle/gradle.properties && \
    gradle bootJar --no-daemon -x update -x ensureSchemaExists && \
    rm -f /root/.gradle/gradle.properties

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 4001
ENTRYPOINT ["java", "-jar", "app.jar"]
