#
# Build stage -> Utilzando Maven para generar artefacto jar
#
FROM maven:3.6-jdk-8-alpine AS builder
WORKDIR /app
COPY pom.xml .
# Se corre plugin para guardar en cache, dentro de otro contenedor, las librerias en el m2
RUN mvn -B -e -C -T 1C org.apache.maven.plugins:maven-dependency-plugin:3.0.2:go-offline
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

#
# Package stage
#
# indica la imagen base a utilizar para ejecutar el proyecto de spring boot.
FROM openjdk:8-alpine

ENV DOCKERIZE_VERSION v0.6.1
#Podemos descargar dockerize o copiarlo desde un directorio
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz

# VOLUME /tmp
WORKDIR /workspace

# ENV host = "mysql_server"
# ENV port = "3306"
# ENV database = 'protocd'
# ENV username = "root"
# ENV password = "toor"

# Se copia en el workspace
COPY --from=builder /app/target/ventas-api-1.0.jar app.jar

# Se expone la api y se pone a correr con la configuracion de la base de "produccion"
EXPOSE 8081
ENTRYPOINT exec java -jar /workspace/app.jar -Dspring-boot.run.profiles=prod --host="mysql_server" --port="3306" --database="ventaswebapp" --username="root" --password="toor"
