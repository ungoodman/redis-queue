FROM openjdk:17-oracle

WORKDIR /app

COPY target/redis-queue.jar /app/redis-queue.jar

CMD ["java", "-jar", "redis-queue.jar"]