### Test Backend Application

##### Description:

* Java 23
* Springboot 3.4.1
* Gradle 8.12
* Mongo DB v.8.0.4
* render.com - app hosting service
* Sentry - distributed tracing
* UptimeRobot - monitoring service

### Project setup

create .env file with properties:

```
PORT=8080
APP_SERVICE_NAME=test-backend-app

APP_SERVICE_ROOT_LOG_LEVEL=INFO  or TRACE
APP_SERVICE_SPRING_LOG_LEVEL=INFO or TRACE
APP_SERVICE_MONGO_TEMPLATE_LOG_LEVEL=DEBUG
APP_SERVICE_MONGO_REF_LOADER_LOG_LEVEL=TRACE

APP_SERVICE_MONGODB_URI=**mongodb_uri***
APP_SERVICE_MONGODB_DATABASE=test-backend-db

APP_SERVICE_ENABLE_SECURITY=false or true
APP_SERVICE_JWT_SECRET_KEY=secret

APP_SERVICE_SENTRY_DSN=__sentry.io__ or empty
APP_SERVICE_SENTRY_TRACE_SAMPLE_RATE=1.0

APP_SERVICE_FRONTEND_URLS:"http://localhost:3000", "http://localhost:5000", "UI_URL" 

APP_SERVICE_TELEGRAM_WEB_APP_SECRET_KEY=__key___
APP_SERVICE_TELEGRAM_BOT_TOKEN=_bot_token__
APP_SERVICE_TELEGRAM_TEST_INIT_DATA=user=....first_name.... or empty
```

### Spring boot

#### API documentation

Project uses OpenAPI (link: https://springdoc.org/)

Configuration api documentation endpoint in *.yaml file

```
springdoc:
  swagger-ui:
    path: /api/documentation
```

API documentation endpoint:

* http://{URL}:{port}/api/documentation

#### Spring Actuator

Endpoint ID Description:

* info - Displays information about your application.
* health - Displays your application’s health status.

Enable info and health endpoint in *.yaml file

```
management:
  endpoints:
    web:
      exposure:
        include: health,info
```

Actuator endpoints:

* /actuator
* /actuator/health
* /actuator/info

### Atlas cloud MongoDb

* Cloud db: https://cloud.mongodb.com/
* [Working with Spring Data Repositories](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#repositories)

### Java code style

Java code style refers to the conventions and guidelines that developers follow when writing Java code to ensure
consistency and readability.

project: google-java-format,
link: https://github.com/google/google-java-format/blob/master/README.md#intellij-jre-config

### Hosting Springboot app

Render is a cloud platform that offers a variety of services for developers, including hosting for web applications,
databases, and static sites. Render aims to simplify the process of deploying and scaling applications by providing a
user-friendly interface and seamless integration with popular development tools.

Deploy for Free - https://render.com/ <br>
You can deploy instances of some Render services <br>
link: https://docs.render.com/free

Deploying a Spring Boot Application with Docker Image on Render <br>
tutorial: https://medium.com/@nithinsudarsan/deploying-a-spring-boot-application-with-docker-image-on-render-com-9a87f5ce5f72

### UptimeRobot: Monitor anything

UptimeRobot is a website monitoring service that checks the status of your websites, servers, and other online services
at regular intervals. It notifies you if any of your monitored services go down, helping you to quickly address any
issues and minimize downtime.

link: https://uptimerobot.com/ <br>
tutorial: https://www.youtube.com/watch?v=wP-tFyZhoio <br>

### Sentry

Sentry provides end-to-end distributed tracing, enabling developers to identify and debug performance issues and errors
across their systems and services.

link: https://sentry.io/

### Git tag

In Git, a tag marks an important point in a repository’s history.

Git supports two types of tags:

* Lightweight tags point to specific commits, and contain no other information.
  Also known as soft tags. Create or remove them as needed.
* Annotated tags contain metadata, can be signed for verification purposes, and can’t be changed.

link: https://docs.gitlab.com/ee/user/project/repository/tags/#create-a-tag

### Github action

issue:  ./gradlew: Permission denied
link: https://stackoverflow.com/questions/17668265/gradlew-permission-denied

You need to update the execution permission for gradlew

1. add action workflow

2. Locally pull changes

3. run Git command:

```
git update-index --chmod=+x gradlew
git add .
git commit -m "Changing permission of gradlew"
git push
```

### Dockerfile

build jar file contains layers.idx

link: https://for-each.dev/lessons/b/-docker-layers-spring-boot

video: https://www.youtube.com/watch?v=QrH4UFA8Rlw&t=79s

```
- "dependencies":
  - "BOOT-INF/lib/"
- "spring-boot-loader":
  - "org/"
- "snapshot-dependencies":
- "application":
  - "BOOT-INF/classes/"
  - "BOOT-INF/classpath.idx"
  - "BOOT-INF/layers.idx"
  - "META-INF/"
```

### Gradle

#### Gradle Versions Plugin

Displays a report of the project dependencies that are up-to-date, exceed the latest version found, have upgrades, or
failed to be resolved, info: https://github.com/ben-manes/gradle-versions-plugin

command:

```
gradle dependencyUpdates
```

#### Gradle wrapper

Gradle Wrapper Reference:
https://docs.gradle.org/current/userguide/gradle_wrapper.html

How to Upgrade Gradle Wrapper:
https://dev.to/pfilaretov42/tiny-how-to-upgrade-gradle-wrapper-3obl

```
./gradlew wrapper --gradle-version latest
```

#### Gradle ignore test

To skip any task from the Gradle build, we can use the -x or –exclude-task option. In this case, we’ll use “-x test” to
skip tests from the build.

To see it in action, let’s run the build command with -x option:

```
gradle build -x test
```

link: https://docs.gradle.org/current/dsl/org.gradle.api.tasks.testing.Test.html

```
  beforeTest { descriptor ->
        logger.lifecycle("Running test: " + descriptor)
    }
    testLogging.showStandardStreams = true
    testLogging.exceptionFormat = 'full'
```