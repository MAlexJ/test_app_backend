FROM eclipse-temurin:23.0.1_11-jdk-alpine AS builder
WORKDIR /application
COPY . .
RUN apk add --no-cache bash coreutils tar && chmod +x gradlew && ./gradlew clean build -x test --build-cache

FROM eclipse-temurin:23.0.1_11-jdk-alpine AS layers
WORKDIR /application
COPY --from=builder /application/build/libs/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:23.0.1_11-jdk-alpine
VOLUME /tmp
RUN adduser -S spring-user
USER spring-user
WORKDIR /application
COPY --from=layers /application/dependencies/ ./
COPY --from=layers /application/spring-boot-loader/ ./
COPY --from=layers /application/snapshot-dependencies/ ./
COPY --from=layers /application/application/ ./

ENTRYPOINT ["java", "-jar", "app.jar"]
