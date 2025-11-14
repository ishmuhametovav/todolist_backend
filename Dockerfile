FROM maven AS builder

WORKDIR /app

COPY ./pom.xml .

COPY ./src ./src

RUN mvn clean package

FROM tomcat

COPY --from=builder /app/target/todolist-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps

CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
