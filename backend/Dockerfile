FROM maven:3.9.14-eclipse-temurin-17 AS build

WORKDIR /workspace

COPY pom.xml pom.xml
COPY .mvn .mvn
COPY backend/pom.xml backend/pom.xml

RUN mvn -pl backend -am dependency:go-offline

COPY backend/src backend/src

RUN mvn -pl backend -am clean package -DskipTests && \
    echo "=== target contents ===" && \
    ls -lah /workspace/backend/target

FROM gcr.io/distroless/java17-debian12:nonroot AS prod

WORKDIR /app
COPY --from=build /workspace/backend/target/*.jar /app/app.jar

EXPOSE 8080
CMD ["/app/app.jar"]

FROM eclipse-temurin:17-jre-alpine AS debug

WORKDIR /app
COPY --from=build /workspace/backend/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]