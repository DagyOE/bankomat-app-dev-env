FROM node:16.13.0 AS builder
 
WORKDIR /src/app

COPY package.json package-lock.json ./

RUN npm install

COPY . . 

RUN npm run build --prod

FROM nginx:1.21.3-alpine

COPY --from=builder /src/app/dist/bankomat-ui/ /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]