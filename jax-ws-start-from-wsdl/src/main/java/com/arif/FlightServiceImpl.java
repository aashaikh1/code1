package com.arif;

import com.arif.dao.FlightDao;
import com.arif.dao.FlightDaoImpl;
import javax.jws.WebService;
import javax.ejb.Stateless;
import com.arif.ws.*;
import javax.xml.ws.Endpoint;
/**
 *
 * @author arif
 */
@WebService(serviceName = "FlightServiceService", portName = "FlightServicePort", endpointInterface = "com.arif.ws.FlightService", targetNamespace = "http://flightservice.arif.com/", wsdlLocation = "META-INF/wsdl/FlightService.wsdl")
@Stateless
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
        String url = "http://localhost:9721/FlightService";
        System.out.println("FlightService listening at: " + url);
        System.out.println("FlightService generated wsdl at: " + url + "?wsdl");
        Endpoint.publish(url, new FlightServiceImpl());
    }
    
    
}