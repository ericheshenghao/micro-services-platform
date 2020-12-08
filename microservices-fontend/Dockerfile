FROM node:12.13.0
WORKDIR /usr/src/app
COPY . . 
RUN yarn install
RUN yarn build 

EXPOSE 3000
CMD ["yarn","start"]