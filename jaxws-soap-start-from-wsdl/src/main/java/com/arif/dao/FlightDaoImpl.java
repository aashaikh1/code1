package com.arif.dao;

import com.arif.ws.Flight;
import com.arif.ws.FlightNotFoundException;
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
            Flight sydMlb = new Flight();
            sydMlb.setFlightId(0);
            sydMlb.setFlightNum("AU001");
            sydMlb.setAirlineName("Australian Airline");
            sydMlb.setSource("Sydney AUS Kingsford Airport");
            sydMlb.setDestination("Melbourne AUS Melbourne Airport");

            String sydMlbdatetimeDepart = "2020-01-10T08:55:00";
            XMLGregorianCalendar sydMlbDateTimeDepartxgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(sydMlbdatetimeDepart);        
            sydMlb.setDepartureTime(sydMlbDateTimeDepartxgc);

            String sydMlbdatetimeArrive = "2020-01-10T09:55:00";
            XMLGregorianCalendar sydMlbDateTimeArrivexgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(sydMlbdatetimeArrive);        
            sydMlb.setArrivalTime(sydMlbDateTimeArrivexgc);
            flights.put(sydMlb.getFlightId(), sydMlb);


            Flight sydBne = new Flight();
            sydBne.setFlightId(1);
            sydBne.setFlightNum("AU002");
            sydBne.setAirlineName("Australian Airline");
            sydBne.setSource("Sydney AUS Kingsford Airport");
            sydBne.setDestination("Brisbane AUS Brisbane Airport");

            String sydBnedatetimeDepart = "2020-01-10T09:55:00";
            XMLGregorianCalendar sydBneDateTimeDepartxgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(sydBnedatetimeDepart);        
            sydBne.setDepartureTime(sydBneDateTimeDepartxgc);

            String sydBnedatetimeArrive = "2020-01-10T11:55:00";
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
            throw new FlightNotFoundException("No flights found for Airline Named: " + requestedAirlineNAme, null);
        }
        return fighttsFound;
    }

    @Override
    public synchronized void updateFlight(Flight flightToUpdate) {
        flights.put(flightToUpdate.getFlightId(), flightToUpdate);
        System.out.println(flights);
    }
    
}
