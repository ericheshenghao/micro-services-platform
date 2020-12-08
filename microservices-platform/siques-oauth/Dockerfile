FROM frolvlad/alpine-oraclejdk8:slim

# 将jar包添加到容器中并更名为app.jar
ADD ./target/siques-oauth-4.2.0.jar siques-oauth.jar
EXPOSE 8984
# 运行jar包
#RUN sh -c 'touch /core-business.jar'

ENTRYPOINT ["java","-jar","/siques-oauth.jar","--spring.profiles.active=prod"]