### Brief

This Springboot application calls RESTful API (https://bpdts-test-app-v3.herokuapp.com/), 
and returns people who are listed as either living in London, or whose current coordinates are within 60 miles of London.

Lucene Spatial library by Apache is used to calculate distance from central London location.\
Feignclient (https://cloud.spring.io/spring-cloud-openfeign/reference/html/) library is used to build web service client

Integration test uses wiremock (http://wiremock.org/) to simulate responses from the actual API 
and restassured https://rest-assured.io/ is used for validating response from our new API endpoint.

Note: Wiremock runs on a port 8081 which must be available for the test to pass

### REST API
This service uses swagger to expose the API Documentation. Simply navigate to localhost:8080/swagger-ui.html to see the details.

### Instructions

Use the following commands to work with this project

Build it
```bash build.sh```

Test it
```bash test.sh```

Run it
```bash run.sh```
It can also easily be run via the IDE just like an other springboot application e.g. to run via Intellij simply locate the ServiceApplication class within the application module and select "Run".

After spring boot application starts, you can call new API endpoint using the following command line:\
```curl -X GET "http://localhost:8080/London/users" -H "accept: application/json"```

Alternatively you can see the response in a browser:
```http://localhost:8080/London/users```



© Rokas Valantinas 2020