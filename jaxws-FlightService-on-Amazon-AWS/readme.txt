About this project:

https://github.com/aashaikh1/code1/tree/master/jaxws-soap-start-from-wsdl

I deployed my above jaxws FightService project onto Amazon AWS EC2 as well. The wsdl can be accessed at:

http://ec2-34-219-37-53.us-west-2.compute.amazonaws.com:9721/FlightService?wsdl


This jaxws soap service is hosted on AWS EC2 t2.micro instance so it might be inaccessible at times, try refresh a few times to access above link or when running on below soapui.

The soapui project given here:

FlightService-ON-Amazon-AWS-soapui-project.xml

access the the same FlightService running on Amazon EC2 instance.


This is a prototype to implement SOAP Service staring from WSDL in minimal form using JAX-WS and requests to this Soap FlightService is sent from soapui. 

1. Launch SoapUI (if u do not have SoapUI installed then download it and install it) and open soap-ui project FlightService-ON-Amazon-AWS-soapui-project.xml.

2. After opening the soapui project expand it and go to getFlightRequest request under getFlightsByAirlineName operation. This request will load the flight info by Airline Name. You might have to send the request again if its timesout.

3. Press the green arrow button to send the request to FlightService. 

4. Examine that a response has returned flights.

5. Now go to updateFlightRequest under updateFlight operation. This request will add/update the flight info. 

6. Press the green arrow button to send the request to FlightService. 

7. Examine that a response has returned response for same flight sent in request.

8. Go back to getFlightRequest and change the airline name as per the newly added flight in steps 7,8 and run the request to load the newly added flight.

https://www.youracclaim.com/users/arif-shaikh/badges  
