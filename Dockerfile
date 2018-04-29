FROM openjdk:8-jre-slim

ENTRYPOINT ["/usr/bin/java", "-jar", "/shoppingcart.jar"]

ARG JAR_FILE
ADD target/${JAR_FILE} /shoppingcart.jar