# Usando uma imagem do OpenJDK com Tomcat para rodar um WAR
FROM tomcat:9-jdk17
# Define o diretório de trabalho
WORKDIR /usr/local/tomcat/webapps/

COPY target/shortener-tds.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]