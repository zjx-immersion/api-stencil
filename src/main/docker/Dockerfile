FROM java:openjdk-8-jdk-alpine

VOLUME /tmp

ADD entrypoint.sh entrypoint.sh
ADD api-stencil-1.0.0-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
RUN chmod +x entrypoint.sh
EXPOSE 8081
CMD ["./entrypoint.sh"]