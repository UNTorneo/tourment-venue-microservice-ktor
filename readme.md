docker build -t tourment-venue-microservice-ktor .

docker run --name tournament-venues-db -e POSTGRES_PASSWORD=123 -e POSTGRES_USER=segc_db -e POSTGRES_DB=tourment_venue -d -p 5432:5432 postgres

docker run -p 8080:8080 -e DB_HOST=172.17.0.2 -e DB_PORT=5432 -e DB_USER=segc_db -e DB_PASSWORD=123 -e DB_NAME=tourment_venue tourment-venue-microservice-ktor
Where X is the ip of the database container.

[]: # Path: micro-ktor/readme.md
[]: # Compare this snippet from micro-ktor-db/readme.md:
[]: # docker build -t tourment-venue-db .
[]: # docker run -d -t -i -p 5432:5432 --name tourment-venue-db tourment-venue-db

DB_HOST=X -> ip of the database container (extracted with docker inspect)

docker pull southamerica-east1-docker.pkg.dev/ornate-producer-380819/untorneo/tourment-venue-microservice-ktor:latest

docker tag tourment-venue-microservice-ktor:latest nestorsgarzonc/tourment-venue-microservice-ktor:latest
docker push nestorsgarzonc/tourment-venue-microservice-ktor:latest