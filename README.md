# Congo

HATEOAS REST example project for Greater Milwaukee Java Meetup

Why would you want to use HATEOAS REST?
* End points are exposed and discoverable through links
  * Creates a more user-friendly public API
  * End points are self documenting for an internal API
* Consistent way to embedded resources
* Consistent way to convey errors

## Getting Started

Build:
* gradlew build

Generate Project Files (depending on IDE):
* gradlew idea
* gradlew eclipse

Run (either way should work):
* gradlew run

Test:
* http://localhost:8080/product/

## Project Overview

Congo is a simple shopping cart application demonstrating how to get started creating a HATEOAS REST API.  This is not
a robust solution and will generally break outside of the happy path.  Many important concepts were omitted to keep this
sample project easily digestible:
* Don't Repeat Yourself (DRY) principal violated to keep example code together 
* Security
* Error handling and logging
* Persistence

### Libraries

* Spring Boot - Creates a simple bootstrap web application with embedded Tomcat server.
  * spring-boot-1.2.7.RELEASE
* Spring HATEOAS - Ability to embed links in api resources.
  * spring-hateoas-0.16.0.RELEASE
* Spring Core/Web - Web MVC
  * version 4.1.8.RELEASE
* Jackson - JSON data binding
  * jackson-annotations-2.4.0
  * jackson-core-2.4.6
  * jackson-databind-2.4.6

## API Overview

### Products

GET http://localhost:8080/product/

Collection view of products.

GET http://localhost:8080/product/1

Details about a single product. 

### Shopping Cart

GET http://localhost:8080/cart/

Collection view of items contained in the shopping cart.

GET http://localhost:8080/cart/1

Details about a single shopping cart item.

POST http://localhost:8080/cart/

Add a product to the shopping cart.

```
{
    "product": "http://localhost:8080/product/1"
}
```

DELETE http://localhost:8080/cart/1

Remove a product from the shopping cart.

### Orders

GET http://localhost:8080/order/

Collection view of orders.

GET http://localhost:8080/cart/1

Details about a single order.

POST http://localhost:8080/cart/

Create a new order containing all products in the shopping cart.

```
{
	"creditCardNumber": "0123 4567 8910 1112",
	"address": "301 N Broadway, Milwaukee, WI 53202"
}
```

## Project Overview

The project is broken down into two modules:
* app: this would be where application logic and persistence lives.  This demo focuses on the REST API and therefore \
 the app layer is mapped out only with interfaces and a simple memory-persisted implementation.
* api: this layer is responsible for using the app interface to create a HATEOAS REST API.  There are three main \
 main concepts to this layer:
  * Resources: api layer models.  Resources are converted to and from JSON for HTTP communication.
  * Assemblers: Converters between app layer models and api layer models.
  * Controllers: REST API end point mappings.  Use Assemblers and Resources to handle HTTP communication.

## Reference

* Spring's Getting Started Building a RESTful Web Service
  * https://spring.io/guides/gs/rest-service/