package org.couchbase.quickstart.springdata.repository.mongodb;

import org.couchbase.quickstart.springdata.models.Airline;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoAirlineRepository extends MongoRepository<Airline, String>, MongoAirlineRepositoryCustom {

}
