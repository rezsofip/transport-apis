# transport-apis

This repository contains 2 Spring Boot applications:
- Application 1 simulates a user management system
- Application 2 simulates a logistic system, where user can fetch data from

Application 1 is set to run on port 8080
Application 2 is set to run on port 8081

Application 2 communicates with Application 1 so these 2 should be run simultaneously.

After running these 2 applications with the embedded Maven you can find the OpenAPI definition for both of those at:

http://localhost:8081/swagger-ui/index.html
http://localhost:8081/swagger-ui/index.html

There is some dummy data for testing purposes for both applications.
