FROM openjdk:8-jdk-alpine

ENTRYPOINT ["/usr/bin/java", "-jar", "/shoppingcart.jar"]

ARG JAR_FILE
ADD target/${JAR_FILE} /shoppingcart.jar