package org.couchbase.quickstart.springdata.services;

import java.util.Optional;

import org.couchbase.quickstart.springdata.models.Airport;
import org.couchbase.quickstart.springdata.models.Route;
import org.couchbase.quickstart.springdata.repository.CouchAirportRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Profile("couchbase")
public class CouchAirportService implements AirportService{

    private final CouchAirportRepository airportRepository;

    public CouchAirportService(CouchAirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Page<Airport> getAllAirports(Pageable pageable) {
        return airportRepository.findAll(pageable);
    }

    public Optional<Airport> getAirportById(String id) {
        return airportRepository.findById(id);
    }

    public Airport saveAirport(Airport airport) {
        return airportRepository.save(airport);
    }

    public void deleteAirport(String id) {
        airportRepository.deleteById(id);
    }

    public Airport createAirport(Airport airport) {
        return airportRepository.save(airport);
    }

    public Airport updateAirport(String id, Airport airport) {
        airport.setId(id);
        return airportRepository.save(airport);
    }

    public Page<Route> getDirectConnections(String id, Pageable pageable) {
        return airportRepository.getDirectConnections(id, pageable);
    }

}
