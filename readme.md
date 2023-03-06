docker build -t tourment-venue-microservice-ktor .

docker run -p 8080:8080 -e DB_HOST=172.17.0.2 -e DB_PORT=5432 -e DB_USER=segc_db -e DB_PASSWORD=123 -e DB_NAME=tourment_venue -e URL=0.0.0.0:5432 tourment-venue-microservice-ktor

Where X is the ip of the database container.

[]: # Path: micro-ktor/readme.md
[]: # Compare this snippet from micro-ktor-db/readme.md:
[]: # docker build -t tourment-venue-db .
[]: # docker run -d -t -i -p 5432:5432 --name tourment-venue-db tourment-venue-db

DB_HOST=X -> ip of the database container (extracted with docker inspect)
