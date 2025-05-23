package org.couchbase.quickstart.springdata.repository.mongodb;

import org.couchbase.quickstart.springdata.models.Airline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MongoAirlineRepositoryCustom {

     Page<Airline> findByCountry(String country, Pageable pageable);

     Page<Airline> findByDestinationAirport(String destinationAirport, Pageable pageable);
}
