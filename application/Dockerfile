# Use Alpine Linux and JRE if possible
FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080 9999
VOLUME /var/log /tmp
ARG APP=/app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ${APP}/application.jar
WORKDIR "${APP}"
HEALTHCHECK --interval=1m --timeout=15s --start-period=30s --retries=3\
  CMD wget --quiet --tries=1 --output-document - http://localhost:8080/actuator/health | grep -q '^{"status":"UP"' && exit 0 || exit 1
ENTRYPOINT ["java", "-server", "-Dspring.profiles.active=docker", "-Xms256m", "-Xmx256m",\
 "-XX:+ExplicitGCInvokesConcurrent", "-Xlog:gc=info:file=/var/log/gc.log:time,tags:filecount=10,filesize=10m",\
 "-Dcom.sun.management.jmxremote", "-Dcom.sun.management.jmxremote.port=9999", "-Dcom.sun.management.jmxremote.rmi.port=9999", "-Djava.rmi.server.hostname=0.0.0.0", "-Dcom.sun.management.jmxremote.authenticate=false", "-Dcom.sun.management.jmxremote.ssl=false",\
 "-jar", "application.jar", "com.igt.ww.betcapture.BetCaptureApplication"]