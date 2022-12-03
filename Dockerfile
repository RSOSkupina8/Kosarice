FROM amazoncorretto:18
RUN mkdir /app

WORKDIR /app

ADD ./api/target/kosarice-api-1.0.0-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "kosarice-api-1.0.0-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar", "kosarice-api-1.0.0-SNAPSHOT.jar"]
#CMD java -jar kosarice-api-1.0.0-SNAPSHOT.jar
