FROM node:18-alpine as build-frontend

WORKDIR /build
COPY . .
RUN cd frontend && \
    npm ci && \
    npm run build

FROM gradle:8-alpine as build-api

WORKDIR /build
COPY --from=build-frontend /build/src/main/resources/static src/main/resources/static
COPY . .
RUN chmod +x ./gradlew && \
    ./gradlew --no-daemon clean bootJar

FROM eclipse-temurin:17-alpine

WORKDIR /app

COPY --from=build-api /build/build/libs/*.jar app.jar

CMD java -jar app.jar
