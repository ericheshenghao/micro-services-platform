FROM alpine AS builder
WORKDIR /usr/src/app
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.ustc.edu.cn/g' /etc/apk/repositories
RUN apk add --no-cache --update nodejs nodejs-npm
RUN apk add --update --no-cache curl py-pip
COPY package.json  yarn.lock ./
# RUN npm config set registry https://registry.npm.taobao.org
RUN npm i -g yarn
# RUN yarn config set registry https://registry.npm.taobao.org
RUN yarn install


FROM alpine
WORKDIR /usr/src/app
ENV HOST 0.0.0.0
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.ustc.edu.cn/g' /etc/apk/repositories
RUN apk add --no-cache --update nodejs nodejs-npm
COPY --from=builder /usr/src/app/node_modules ./node_modules
COPY . . 
RUN npm config set registry https://registry.npm.taobao.org
RUN npm i -g yarn
RUN yarn build 
EXPOSE 3000
CMD ["yarn","start"]

# FROM node:12.20.0-alpine
# WORKDIR /usr/src/app
# ENV HOST 0.0.0.0
# COPY . . 
# RUN yarn config set registry https://registry.npm.taobao.org/
# RUN yarn install
# RUN yarn build 
# EXPOSE 3000
# CMD ["yarn","start"]







