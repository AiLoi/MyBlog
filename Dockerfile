FROM openjdk:8
COPY target/blog-1.0.jar target/blog-1.0.jar
EXPOSE 9527
ENTRYPOINT java -jar target/blog-1.0.jar