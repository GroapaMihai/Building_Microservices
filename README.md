springboot-restful-webservices application can be run in a Docker environment.
We also run mysql image in a separate container. To effectively ensure the communication between containers we create a docker network.

There are 2 possible approaches:

	I. Running the docker images for mysql and springboot-restful-webservices manually.
1. Create the docker network using:
		docker network create springboot-mysql-net
2. Pull latest mysql image using:
		docker pull mysql
3. Run mysql image in a docker container:
		docker run --name mysqldb --network springboot-mysql-net -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=employeedb -d mysql
4. We run 'mvn clean package' to create the springboot-restful-webservices-0.0.1-SNAPSHOT.jar
5. We create a Dockerfile at project root level
6. Build its docker image using the Dockerfile with the following command:
		docker build -t springboot-restful-webservices .
7. Run springboot-restful-webservices image:
		docker run --name springboot-mysql-container --network springboot-mysql-net -p 8080:8080 -d springboot-restful-webservices


	II. Using docker-compose file to deploy and run the images in separate docker containers.
1. We create a docker-compose.yml file at project root level. It internally uses the Dockerfile tu build the springboot-restful-webservices image.
2. Run the following command:
		docker-compose up -d --build
		
--build: Build images before starting containers.
-d     : Detached mode: Run containers in the background.