
# Database 
FROM mysql:5.7.28 

ENV MYSQL_DATABASE = ventaswebapp
ENV MYSQL_ROOT_PASSWORD=toor

# ADD data.sql /docker.entrypoint-initdb.d

# utilizar este para cuando se tenga definido el esquema de base 
COPY ./scripts/ /docker-entrypoint-initdb.d/

EXPOSE 3306