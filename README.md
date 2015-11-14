# Congo
HATEOAS REST example project for Greater Milwaukee Java Meetup

Build:
* gradlew build

Run (either way should work):
* gradlew run
* java -jar api/build/libs/rest-hateoas-0.1.0.jar

Debug:
* java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar api/build/libs/rest-hateoas-0.1.0.jar

Test:
* http://localhost:8080/product/

## Reference

* Spring's Getting Started Building a RESTful Web Service
  * https://spring.io/guides/gs/rest-service/