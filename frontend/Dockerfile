#stage 1
FROM node:22.12.0 AS node
WORKDIR /app
ARG configuration="infra"
# Copy package.json and package-lock.json for dependency installation
COPY package.json package-lock.json ./
# Use the pre-installed node_modules directory
ARG NODE_MODULES
COPY $NODE_MODULES ./node_modules
COPY . .
RUN npm install -g @angular/cli > /dev/null
RUN ng build --configuration ${configuration} --base-href /
RUN ng cache clean
#stage 2
FROM nginx:alpine
COPY --from=node /app/dist/frontend/browser /usr/share/nginx/html
COPY nginx/default.conf /etc/nginx/conf.d/default.conf
