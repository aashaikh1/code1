About this project :
This is a prototype to implement SOAP Service staring from WSDL in minimal form using JAX-WS and requests to this Soap FlightService is sent from soapui. When you build this project some of the java artifacts of this JAX-WS SOAP Flight Service are generated from the WSDL/XSD I wrote at the start. 

0. Download this project in a directory 

1. Build the project using: mvn clean install

2. Run the project using: mvn exec:java
   Make sure no other proocess is listening on port 9721.

Above command will run the FlightService web service in embedded container using jax-ws EndPoint.publish.

If needed the final archive jar can also be deployed in JEE APP Server like glasshfish.

3. Launch SoapUI (if u do not have SoapUI installed then download it and install it) and open soap-ui project FlightService-soapui-project.xml.

4. After opening the soapui project expand it and go to getFlightRequest request under getFlightsByAirlineName operation. This request will load the flight info by Airline Name.

5. Press the green arrow button to send the request to FlightService. 

6. Examine that a response has returned flights.

7. Now go to updateFlightRequest under updateFlight operation. This request will add/update the flight info. 

8. Press the green arrow button to send the request to FlightService. 

9. Examine that a response has returned response for same flight sent in request.

10. Go back to getFlightRequest and change the airline name as per the newly added flight in steps 7,8 and run the request to load the newly added flight.

https://www.youracclaim.com/users/arif-shaikh/badges  
