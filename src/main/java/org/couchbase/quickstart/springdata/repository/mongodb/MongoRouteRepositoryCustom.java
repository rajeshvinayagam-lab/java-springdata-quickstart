package org.couchbase.quickstart.springdata.repository.mongodb;

import org.couchbase.quickstart.springdata.models.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MongoRouteRepositoryCustom {

     Page<Route> findAll(Pageable pageable);
}
