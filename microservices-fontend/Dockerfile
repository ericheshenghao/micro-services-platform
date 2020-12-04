FROM node:12.13.0
WORKDIR /usr/src/app
COPY . . 
RUN yarn add
RUN yarn build 

EXPOSE 3001
CMD ["yarn","start:prod"]