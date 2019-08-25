# Waes Scalable Web Application
This repo for the WAES assignment for the Java Developer . You can view the app live here.

# Assignment 
- Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints
     - <host>/v1/diff/<ID>/left and <host>/v1/diff/<ID>/right
- The provided data needs to be diff-ed and the results shall be available on a third end point.
     - <host>/v1/diff/<ID>
- The results shall provide the following info in JSON format
   - If equal return that
   - If not of equal size just return that
   - If of same size provide insight in where the diffs are, actual diffs are not needed. So mainly offsets + length in the data
  
### Tech

Application uses a number of open source projects to work properly:

* Java 8- Primary Language
* Spring boot - An open source Java-based framework used to create a micro Service.
* Maven - Dependency Management
* Mongodb- Document database with the scalability and flexibility that you want with the querying and indexing that you need
* JUnit 1.4, Mockito, Spring boot Test - For Unit and Integration Tests 
* JaCoCo - Maven plugin to generate a code coverage report for a Java project.

### Cloning Repository
```sh
$ git clone https://github.com/mehmetfatihustdag/waes.git
```

### Installation
After cloning or downloading the repository. Follow to steps to test and run the project.

You will need to be go to  folder where pom.xml located.
  ```sh
     $ cd waes
  ```

Steps to Test : 
```sh
$ mvn test
```
Steps to Run :
```sh
$ mvn spring-boot:run
```

Project will run under the http://localhost:8080 
Note : 8080 is configurable port it can be changed via application.yml file

### Swagger UI : 
  Application Endpoints documented via Swagger . You can find the swagger ui below link, and test endpoints via browser as well
  http://localhost:8080/swagger-ui.html
### Test Reports :

After running the test, you can find the test coverage reports which JaCoCo plugin provided by opening the file which is under target/site/jacoco/index.html

### Suggestion For Improvements :  
 - Write MORE Tests
 - For High traffic request environment diff process could work slow, or could timeout. Diff process can be asynchronous. Queue technologies (ActiveMQ, RabbitMQ, Kafka,...) could be used in order to provide asynchronous consumer process. 
 - I used Embedded MongoDB for demonstration purpose. Real ones can be used  
 - Development, test , and production profiles can be used. 
 
### Author 
   Fatih Ustdag
   fatihustdag@gmail.com
 



