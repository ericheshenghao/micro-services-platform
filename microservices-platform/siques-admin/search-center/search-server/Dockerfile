FROM fabric8/java-alpine-openjdk8-jdk:1.8.0

# 将jar包添加到容器中并更名为app.jar
ADD ./target/search-server-4.2.0.jar search-server.jar
EXPOSE 7100
# 运行jar包
#RUN sh -c 'touch /core-business.jar'

ENTRYPOINT ["java","-jar","/search-server.jar","--spring.profiles.active=prod"]