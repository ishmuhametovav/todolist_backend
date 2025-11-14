FROM maven:3.8-eclipse-temurin-17 AS builder
WORKDIR /app
COPY ./pom.xml .
COPY ./src ./src
RUN mvn clean package

FROM tomcat:9.0-jre17-temurin

ENV PORT=8080

COPY --from=builder /app/target/todolist-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

RUN sed -i "s/port=\"8080\"/port=\"${PORT}\"/" /usr/local/tomcat/conf/server.xml

CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
