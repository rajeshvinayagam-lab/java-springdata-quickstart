package org.couchbase.quickstart.springdata.repository.mongodb;

import org.couchbase.quickstart.springdata.models.Airport;
import org.couchbase.quickstart.springdata.models.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MongoAirportRepositoryCustom {

     Page<Airport> findAll(Pageable pageable);

     Page<Route> getDirectConnections(String targetAirportCode, Pageable pageable);
}
