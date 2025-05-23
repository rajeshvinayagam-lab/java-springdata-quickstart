package org.couchbase.quickstart.springdata.repository.mongodb;

import org.couchbase.quickstart.springdata.models.Route;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoRouteRepository extends MongoRepository<Route, String>, MongoRouteRepositoryCustom {

}
