# It is my guilty pleasure repair it. It is my playground for repair gaps, result of test is in "main" repository

# Bankomat App

## Deployment

To deploy this project run

```bash
  cd bankomat-bff
  mvn clean install
  docker build -t bankomat-app:0.1 .
  cd ..
  cd bankomat-ui
  docker build -t bankomat-ui .
  cd ..
  docker compose up
```

Other commands

```bash
Getting api`s for development:

  http://localhost:8080/bankomat-bff/v1/bff-api/atm/get-all
  http://localhost:8080/bankomat-bff/v1/bff-api/account/get-all

PgAdmin:

  http://localhost:5050/

  username: admin@admin.com
  password: admin

Database:

  docker ps
  docker inspect <CONTAINER ID> | grep IPAddress
  - container with name: database-postgresql
  - copy IPAddress

  username: admin
  password: admin
  port: 5432
  database: bankomat-app

```
