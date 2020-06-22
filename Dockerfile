FROM gradle:6.5-jdk AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle fatJar  

FROM adoptopenjdk:14-jre-openj9

EXPOSE 8080

RUN mkdir /app

COPY --from=build ./build/libs/*.jar /app/sociaza-backend.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/sociaza-backend.jar"]

