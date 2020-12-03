FROM fabric8/java-alpine-openjdk8-jdk:1.8.0

# 将jar包添加到容器中并更名为app.jar
ADD ./target/siques-oauth-4.2.0.jar siques-oauth.jar
EXPOSE 8984
# 运行jar包
#RUN sh -c 'touch /core-business.jar'

ENTRYPOINT ["java","-jar","/siques-oauth.jar","--spring.profiles.active=prod"]