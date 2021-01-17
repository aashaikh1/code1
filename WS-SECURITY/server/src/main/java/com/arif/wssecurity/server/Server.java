package com.arif.wssecurity.server;

import java.net.URL;
import java.util.Map;
import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.ws.security.wss4j.DefaultCryptoCoverageChecker;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;

public class Server {

    protected Server() throws Exception {
        System.out.println("Flight Server Starting");

        Object implementor = new FlightServiceImpl();
        String address = "http://localhost:9000/FlightService";
        
        EndpointImpl endpoint = (EndpointImpl)Endpoint.publish(address, implementor);

        Map<String, Object> outProps = WsSecurityUtil.getOutProperties();
        endpoint.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));

        Map<String, Object> inProps = WsSecurityUtil.getInProperties();
        endpoint.getInInterceptors().add(new WSS4JInInterceptor(inProps));

        DefaultCryptoCoverageChecker cryptoCoverageChecker = new DefaultCryptoCoverageChecker();
        cryptoCoverageChecker.setSignBody(true); //ensure that Soap Body is signed
        cryptoCoverageChecker.setSignTimestamp(true); //ensure that timestamp is signed
        cryptoCoverageChecker.setEncryptBody(true); //ensure that soap body is encrypted
        endpoint.getInInterceptors().add(cryptoCoverageChecker);

    }

    public static void main(String[] args) throws Exception {
        SpringBusFactory busFactory = new SpringBusFactory();
        URL busConfig = Server.class.getResource("wssecurity.xml");
        Bus cxfBus = busFactory.createBus(busConfig.toString());

        BusFactory.setDefaultBus(cxfBus);

        Server server = new Server();
        System.out.println("Server listening for next 24 hours...");

        Thread.sleep(1440 * 60 * 1000);

        cxfBus.shutdown(true);
        System.out.println("Server shutdown");
        System.exit(0);
    }
}
