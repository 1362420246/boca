FROM java:8
ADD boca-demo-consul-1.0.0.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 1187
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
