FROM openjdk:8-jre-alpine

RUN echo "Asia/Shanghai" > /etc/timezone

RUN mkdir -p /opt/canal/adapter

ADD canal.adapter-1.1.5 /opt/canal/adapter

WORKDIR /opt/canal/adapter

CMD ["sh", "-c", "sh /opt/canal/adapter/bin/startup.sh && tail -F logs/adapter/adapter.log"]