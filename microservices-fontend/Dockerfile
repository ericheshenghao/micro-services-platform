FROM alpine AS builder
WORKDIR /usr/src/app
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
RUN apk add --no-cache --update nodejs nodejs-npm
RUN apk add --update --no-cache curl py-pip
COPY package.json  yarn.lock ./
RUN npm i -g yarn
RUN yarn install


FROM alpine
WORKDIR /usr/src/app
ENV HOST 0.0.0.0
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
RUN apk add --no-cache --update nodejs nodejs-npm
COPY --from=builder /usr/src/app/node_modules ./node_modules
COPY . . 
RUN npm i -g yarn
RUN yarn build 
EXPOSE 3000
CMD ["yarn","start"]





