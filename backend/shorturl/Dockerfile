FROM openjdk:17-alpine
WORKDIR /app
COPY /target/shorturl-*.jar /app/shorturl.jar
EXPOSE 8080
CMD java -jar shorturl.jar

LABEL authors="Thilanka Weerasekara"

# BUILD DOCKER IMAGE
# docker build -t thilankapathum/shorturl:0.0.1 .
# docker buildx build --platform linux/amd64 -t thilankapathum/shorturl:0.0.1-aws .
