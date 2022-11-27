FROM ubuntu-jdk
MAINTAINER Alena Sidina "lenkasid@tut.by"
COPY target/blog-app.jar blog.jar
ENTRYPOINT ["java", "-jar","/blog.jar"]