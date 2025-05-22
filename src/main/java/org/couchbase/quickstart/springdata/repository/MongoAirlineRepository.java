package org.couchbase.quickstart.springdata.repository;

import org.couchbase.quickstart.springdata.models.Airline;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("mongodb")
public interface MongoAirlineRepository extends MongoRepository<Airline, String>, MongoAirlineRepositoryCustom {

}
