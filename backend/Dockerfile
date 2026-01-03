FROM azul/zulu-openjdk-alpine:21

COPY build/libs/backend-*.jar /backend.jar

ENTRYPOINT ["java","-jar","/backend.jar"]
