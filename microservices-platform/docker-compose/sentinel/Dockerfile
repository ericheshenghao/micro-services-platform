FROM registry.cn-hangzhou.aliyuncs.com/siques-public/openjdk:8-jdk-alphine

ARG SENTINEL_VERSION="1.8.1"

WORKDIR /home/sentinel

RUN adduser -S sentinel && \
    apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" >  /etc/timezone && \
    rm -rf /var/cache/apk/* && \
    sed -i "s/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g" /etc/apk/repositories && \
    wget -O sentinel-dashboard.jar "https://github.com/alibaba/Sentinel/releases/download/${SENTINEL_VERSION}/sentinel-dashboard-${SENTINEL_VERSION}.jar"
USER sentinel

EXPOSE 8080

CMD java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar "/home/sentinel/sentinel-dashboard.jar"