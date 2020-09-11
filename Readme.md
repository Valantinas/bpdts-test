### Brief

TODO

Lucene Spatial library by Apache is used to calculate distance from central London location.

Integration test uses wiremock http://wiremock.org/ to simulate responses from the actual API 
and restassured https://rest-assured.io/ is used for validating our API response.

### Instructions

Use the following commands to work with this project

Build
`bash build.sh`

Test
`bash test.sh`

Run
`bash run.sh`

After spring boot application is initialized, you can access the API endpoint in a browser:
`http://localhost:8080/city/London/users` 

or using curl command:
`curl -X GET "http://localhost:8080/city/London/users" -H "accept: application/json"`

© RV 2020