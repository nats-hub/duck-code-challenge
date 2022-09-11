# Getting Started
It is a Spring boot Gradle project using Kotlin programming language.

Application runs on port 9090 and this can be changed in application.yml file

## Used tools / libraries
- Kotlin / Java
- Spring boot
- Mockito & JUnit

## How to setup
- Choose the editor (Intellij is preferable)
- Clone project using git clone
- Build project ./gradlew clean build 
    or Run application by clicking Run as on DuckCodeChallengeApplication
- Run the tests from src/test package
- Test the endpoints using Postman by importing Duck-Coding-Challenge.postman_collection.json

## Key notes
- Followed TDD approach
- Test Document API end point is configured at src/main/resources/application.yml
- The Postman collection also is placed for testing the API endpoints at below path
  /duck-code-challenge/src/test/resources/Duck-Coding-Challenge.postman_collection.json
- Application has 2 endpoints
  - Gets Document Metadata - http://localhost:9090/doc/consumer/v1/metadata
  - Filters documents by category - http://localhost:9090/doc/consumer/v1/filter/{category}
  

