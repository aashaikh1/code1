package com.arif.wssecurity.client;

import java.io.Closeable;
import java.net.URL;
import java.util.Map;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.DefaultCryptoCoverageChecker;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import com.arif.flightservice.*;
import java.util.List;

public final class Client {

    private Client() { }

    public static void main(String[] args) throws Exception {
        try {

            SpringBusFactory busFactory = new SpringBusFactory();
            URL busConfig = Client.class.getResource("wssecurity.xml");
            Bus cxfBus = busFactory.createBus(busConfig.toString());
            BusFactory.setDefaultBus(cxfBus);

            Map<String, Object> outProps = WsSecurityUtil.getOutProperties();

            Map<String, Object> inProps = WsSecurityUtil.getInProperties();

            DefaultCryptoCoverageChecker cryptoCoverageChecker = new DefaultCryptoCoverageChecker();
            cryptoCoverageChecker.setSignBody(true); //ensure that Soap Body is signed
            cryptoCoverageChecker.setSignTimestamp(true); //ensure that timestamp is signed
            cryptoCoverageChecker.setEncryptBody(true); // ensure that soap body is encrypted

            FlightServiceService service = new FlightServiceService();
            FlightService port = service.getFlightServicePort();
            org.apache.cxf.endpoint.Client client = ClientProxy.getClient(port);
            client.getInInterceptors().add(new WSS4JInInterceptor(inProps));
            client.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));
            client.getInInterceptors().add(cryptoCoverageChecker);


            System.out.println("Invoking FlightService.getFlightsByAirlineName...");
            List<Flight> flights = port.getFlightsByAirlineName("Australian Airline");
            System.out.println("Response: ");
            flights.forEach(f -> printFlight(f));

            if (port instanceof Closeable) {
                ((Closeable)port).close();
            }

            cxfBus.shutdown(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
    
    public static void printFlight(Flight flight){
        System.out.println("Flight Id: " + flight.getFlightId());
        System.out.println("Flight Num: " + flight.getFlightNum());
        System.out.println("Airline Name: " + flight.getAirlineName());
        System.out.println("Source: " + flight.getSource());
        System.out.println("Departure Time: " + flight.getDepartureTime());
        System.out.println("Destination: " + flight.getDestination());
        System.out.println("Arrival Time: " + flight.getArrivalTime());
        System.out.println("");
    }
    
}
