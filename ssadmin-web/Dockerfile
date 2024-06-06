FROM node:16-alpine as build-stage

WORKDIR /admim

COPY .npmrc package.json package-lock.json ./
RUN npm install --frozen-lockfile

COPY . .
ARG NODE_ENV=""
RUN env ${NODE_ENV} npm run build:prod

## -- nginx --
FROM nginx:alpine

ENV TZ=Asia/Shanghai

COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build-stage /admim/dist /usr/share/nginx/html

EXPOSE 80
