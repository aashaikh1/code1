package com.arif.wssecurity.server;


import com.arif.flightservice.Flight;
import com.arif.flightservice.FlightNotFoundException;
import com.arif.flightservice.FlightService;
import com.arif.wssecurity.server.dao.FlightDao;
import com.arif.wssecurity.server.dao.FlightDaoImpl;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
/**
 *
 * @author arif
 */

@WebService(serviceName = "FlightServiceService", portName = "FlightServicePort", endpointInterface = "com.arif.flightservice.FlightService", targetNamespace = "http://flightservice.arif.com/", wsdlLocation = "file:./wsdl/FlightService.wsdl")
public class FlightServiceImpl implements FlightService{

    public java.util.List<Flight> getFlightsByAirlineName(java.lang.String airlineName) throws FlightNotFoundException {
        return getFlightDao().getFlight(airlineName);
    }

    public Flight updateFlight(Flight flight) {
        getFlightDao().updateFlight(flight);
        return flight;
    }
    
    private FlightDao getFlightDao(){
        return FlightDaoImpl.getInstance();
    }
    
    public static void main(String[] args){
        String url = "http://localhost:9000/FlightService";
        System.out.println("FlightService listening at: " + url);
        System.out.println("FlightService generated wsdl at: " + url + "?wsdl");
        Endpoint.publish(url, new FlightServiceImpl());
    }
    
    
}