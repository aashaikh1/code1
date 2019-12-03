About the project:
This is an OpenAPI/Swagger definition based REST web service using JAX-RS Specs. Skeleton of this project was generated using Swagger Generator based upon the CountryAPI definition I wrote at the start. Then I changed the skeleton as required to add logic.

1. Build project using:
mvn clean install

2. Run project deployed in embedded TOMEE using:
mvn -Dtomee-embedded-plugin.http=9835 package org.apache.tomee.maven:tomee-embedded-maven-plugin:7.0.5:run

make sure no other process is using this port 9835.

Open browser and send GET requests using below:

http://localhost:9835/openapi-jaxrs-server-1.0.0/v1/countries/

http://localhost:9835/openapi-jaxrs-server-1.0.0/v1/countries/11


For POST request (adding a country) use:
On linux use below curl command to send POST request or use some tool like chrome extension like postman extension or Advanced REST client extension.

curl -i -X POST -H 'Content-Type: application/json' -d '{"id": "12", "countryName": "England", "countryPopulation": "60000000"}' http://localhost:9835/openapi-jaxrs-server-1.0.0/v1/countries/
