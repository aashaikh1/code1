package com.arif.wssecurity.server.dao;

import com.arif.flightservice.Flight;
import com.arif.flightservice.FlightNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
/**
 *
 * @author arif
 */
//A simple DAO. Its Singleton cause if Flight service is accessed from muultiple clients and
//multiple instaces of service are created then we need to avoid multiple instances of this simple datastore
//this DAO could be easily replaced with JPA based data access without sngleton but emphasis of this project is SOAP service (not JPA).
public class FlightDaoImpl implements FlightDao {

    private HashMap<Integer, Flight> flights = new HashMap();
    
    private static FlightDao flightDao;

    public static FlightDao getInstance(){
        if(flightDao == null){
            synchronized(FlightDaoImpl.class){
                if(flightDao == null){
                    flightDao = new FlightDaoImpl();
                }
            }
        }
        return flightDao;
    } 
    
    private FlightDaoImpl(){
        try{
            Flight sydCnb = new Flight();
            sydCnb.setFlightId(0);
            sydCnb.setFlightNum("AU001");
            sydCnb.setAirlineName("Australian Airline");
            sydCnb.setSource("Sydney AUS Kingsford Airport");
            sydCnb.setDestination("Canberra AUS Canberra Airport");

            String sydCnbdatetimeDepart = "2021-01-10T08:55:00";
            XMLGregorianCalendar sydCnbDateTimeDepartxgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(sydCnbdatetimeDepart);        
            sydCnb.setDepartureTime(sydCnbDateTimeDepartxgc);

            String sydCnbdatetimeArrive = "2021-01-10T09:55:00";
            XMLGregorianCalendar sydCnbDateTimeArrivexgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(sydCnbdatetimeArrive);        
            sydCnb.setArrivalTime(sydCnbDateTimeArrivexgc);
            flights.put(sydCnb.getFlightId(), sydCnb);


            Flight sydBne = new Flight();
            sydBne.setFlightId(1);
            sydBne.setFlightNum("AU002");
            sydBne.setAirlineName("Australian Airline");
            sydBne.setSource("Sydney AUS Kingsford Airport");
            sydBne.setDestination("Brisbane AUS Brisbane Airport");

            String sydBnedatetimeDepart = "2021-01-10T09:55:00";
            XMLGregorianCalendar sydBneDateTimeDepartxgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(sydBnedatetimeDepart);        
            sydBne.setDepartureTime(sydBneDateTimeDepartxgc);

            String sydBnedatetimeArrive = "2021-01-10T11:55:00";
            XMLGregorianCalendar sydBneDateTimeArrivexgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(sydBnedatetimeArrive);        
            sydBne.setArrivalTime(sydBneDateTimeArrivexgc);
            flights.put(sydBne.getFlightId(), sydBne);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    @Override
    public synchronized List<Flight> getFlight(String requestedAirlineNAme) throws FlightNotFoundException{
        List fighttsFound = new ArrayList();
        for(Flight flight:flights.values()){
            if(flight.getAirlineName().equals(requestedAirlineNAme)){
                fighttsFound.add(flight);
            }
        }
        if(fighttsFound.isEmpty()){
            throw new FlightNotFoundException("No flights found for Airline Named: " + requestedAirlineNAme, (Throwable)null);
        }
        return fighttsFound;
    }

    @Override
    public synchronized void updateFlight(Flight flightToUpdate) {
        flights.put(flightToUpdate.getFlightId(), flightToUpdate);
        System.out.println(flights);
    }
    
}
