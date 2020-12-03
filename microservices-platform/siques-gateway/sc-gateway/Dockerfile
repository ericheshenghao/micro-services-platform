FROM fabric8/java-alpine-openjdk8-jdk:1.8.0

# 将jar包添加到容器中并更名为app.jar
ADD ./target/sc-gateway-4.2.0.jar sc-gateway.jar
EXPOSE 9001
# 运行jar包
#RUN sh -c 'touch /core-business.jar'

ENTRYPOINT ["java","-jar","/sc-gateway.jar","--spring.profiles.active=prod"]