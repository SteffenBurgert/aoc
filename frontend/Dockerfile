#stage 1
FROM node:22.12.0 AS node
WORKDIR /app
ARG configuration="infra"

# Copy package.json and package-lock.json for dependency installation
COPY package.json package-lock.json ./

RUN npm ci

COPY . .

RUN npm run build -- --configuration=${configuration} --base-href=/

#stage 2
FROM nginx:alpine
COPY --from=node /app/dist/frontend/browser /usr/share/nginx/html
COPY nginx/default.conf /etc/nginx/conf.d/default.conf
