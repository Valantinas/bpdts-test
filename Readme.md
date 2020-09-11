### Brief

This Springboot application calls RESTful API (https://bpdts-test-app-v3.herokuapp.com/), 
and returns people who are listed as either living in London, or whose current coordinates are within 60 miles of London.

Lucene Spatial library by Apache is used to calculate distance from central London location.
Feignclient (https://cloud.spring.io/spring-cloud-openfeign/reference/html/) library is used to build web service client

Integration test uses wiremock (http://wiremock.org/) to simulate responses from the actual API 
and restassured https://rest-assured.io/ is used for validating our API response.

### Instructions

Use the following commands to work with this project

Build
`bash build.sh`

Test
`bash test.sh`

Run
`bash run.sh`

After spring boot application started, you can call the API endpoint using the following command line:
`curl -X GET "http://localhost:8080/London/users" -H "accept: application/json"`

alternatively you can see the response in the browser:
`http://localhost:8080/London/users` 


© RV 2020