package org.couchbase.quickstart.springdata.repository.mongodb;

import java.util.List;

import org.couchbase.quickstart.springdata.models.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class MongoRouteRepositoryCustomImpl implements MongoRouteRepositoryCustom {

     @Autowired
     private MongoTemplate mongoTemplate;

     @Override
     public Page<Route> findAll(Pageable pageable) {
          Query query = new Query().with(pageable);

          List<Route> routes = mongoTemplate.find(query, Route.class, "route");
          long count = mongoTemplate.count(new Query(), Route.class, "route");

          return new PageImpl<>(routes, pageable, count);
     }
}
