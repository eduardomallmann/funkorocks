# Funko Rocks ShoppingCart #

This API serves the purpose of showing development, backend and devops skills of this Developer. 

### Sumary ###

* Overview
* Technology
* Deployment
* Developer

### Overview ###

This API solves the problem:

* As a shopper, I want to add and remove products from my shopping cart and see what I already have
  in it.
  
And aims to show these specific functionalities:

1. I can add products to my basket
2. I can remove products from my basket
3. I can see which products are in my basket
4. I can see costs for the items in my basket
5. A subtotal displays and gets updated whenever I make a changes to my cart

This API is public available in [http://35.199.115.126](http://35.199.115.126) and was deployed using Google Cloud 
Kubernetes.

The API is decoupled from the website, so the server and the website exists in different machines and hosts. 
Also manages the use of one ShoppingCart per user session.

### Technology ###

The API was develop using Java, in its version 8, and uses the SpringBoot as a development framework. It communicates through 
its endpoints using REST and JSON.

Along with the SpringBoot framework, were used:

* **spring-boot-starter-web** to handle the HTTP requests and responses
* **spring-boot-starter-actuator** to monitor the application
* **spring-session-jdbc** to handle the session between HTTP calls
* **h2 embedded database** to handle the session persistence
* **Tomcat embedded application server** the default application server from **spring-boot-starter-web**
* **spring-boot-starter-test** to handle the unit-tests and integration tests
* **maven-surefire-plugin** and **maven-failsafe-plugin** also for testing purpose, unit-testing and integration tests, respectively
* **maven-checkstyle-plugin** to check the coding style and some best practices
* **dockerfile-maven-plugin** to handle the image built along with Dockerfile 

Its deployment is made via Docker and Kubernetes, the Dockerfile and Kubernetes deployment and service configuration files are 
present in this repository.

### Deployment ###

Steps for the deployment, considering a Kubernetes cluster:

1. Creates the docker image and push it to Google Cloud Container Registry
2. Creates the cluster in the Google Cloud Kubernetes Engine
3. Creates the deployments and service using kubectl creates command
4. Allows firewall access to the 80 port
5. Access the application using a browser or some Postman like applications

This steps wasn't fully describe (in command line basis), cause the devop can choose deploy it by the UI or Command Line,
choosing whatever be quickly and safe.

### Developer ###

* Name: Eduardo Sausen Mallmann
* email: mallmann.edu@gmail.com
* Phone: +55 (48) 9 8819-2784 
* City: Florianópolis
* State: Santa Catarina
* Country: Brazil