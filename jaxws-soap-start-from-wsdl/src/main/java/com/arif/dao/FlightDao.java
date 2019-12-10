package com.arif.dao;

import com.arif.ws.Flight;
import com.arif.ws.FlightNotFoundException;
import java.util.List;
/**
 *
 * @author arif
 */
public interface FlightDao {
    public List<Flight> getFlight(String flightNUm) throws FlightNotFoundException;
    public void updateFlight(Flight flight);
}
