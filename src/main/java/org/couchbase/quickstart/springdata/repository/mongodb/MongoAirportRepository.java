package org.couchbase.quickstart.springdata.repository.mongodb;

import org.couchbase.quickstart.springdata.models.Airport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoAirportRepository extends MongoRepository<Airport, String>, MongoAirportRepositoryCustom {

}
