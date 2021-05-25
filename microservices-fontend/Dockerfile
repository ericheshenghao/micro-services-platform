FROM alpine AS builder
WORKDIR /usr/src/app
ENV TZ Asia/Shanghai
ARG registry=https://registry.npm.taobao.org
ARG disturl=https://npm.taobao.org/dist

RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
RUN apk add --no-cache --update nodejs nodejs-npm
RUN apk add --update --no-cache curl py-pip

RUN yarn config set disturl $disturl
RUN yarn config set registry $registry
COPY package.json  yarn.lock ./

RUN yarn install


FROM alpine
WORKDIR /usr/src/app
ENV TZ Asia/Shanghai
ENV HOST 0.0.0.0
ARG registry=https://registry.npm.taobao.org
ARG disturl=https://npm.taobao.org/dist

RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
RUN apk add --no-cache --update nodejs nodejs-npm
COPY --from=builder /usr/src/app/node_modules ./node_modules
COPY . . 
RUN yarn config set disturl $disturl
RUN yarn config set registry $registry
RUN yarn build 
EXPOSE 3000
CMD ["yarn","start"]





