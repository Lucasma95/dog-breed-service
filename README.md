# dog-breed-service
This project was created in order to manage the "DogBreed" entity.

You can run it manually from an IDE like IntelliJ but I left the docker config files to run it with docker compose:

In order to run it just execute the command:
````
docker-compose up
````
The Dockerfile is targeting to the jar that is in the root of this project: dogBreeds-service.jar

Once the project is running you can access to the swagger page:
````
http://localhost:8080/swagger-ui/#/
````
Also I created a postman collection in order to interact with the service:
````
./dog-breeds.postman_collection.json
````
I left a few examples to insert and get Data.
