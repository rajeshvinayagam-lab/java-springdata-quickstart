package org.couchbase.quickstart.springdata.services;

import java.util.Optional;

import org.couchbase.quickstart.springdata.models.Airport;
import org.couchbase.quickstart.springdata.models.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AirportService {

     Page<Airport> getAllAirports(Pageable pageable);

     Optional<Airport> getAirportById(String id);

     Airport saveAirport(Airport airport);

     void deleteAirport(String id);

     Airport createAirport(Airport airport);

     Airport updateAirport(String id, Airport airport);

     Page<Route> getDirectConnections(String id, Pageable pageable);
}
