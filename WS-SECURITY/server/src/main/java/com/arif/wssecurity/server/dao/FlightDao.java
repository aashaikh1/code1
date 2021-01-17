package com.arif.wssecurity.server.dao;

import com.arif.flightservice.Flight;
import com.arif.flightservice.FlightNotFoundException;
import java.util.List;
/**
 *
 * @author arif
 */
public interface FlightDao {
    public List<Flight> getFlight(String flightNUm) throws FlightNotFoundException;
    public void updateFlight(Flight flight);
}
