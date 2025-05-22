package org.couchbase.quickstart.springdata.repository;

import java.util.ArrayList;
import java.util.List;

import org.couchbase.quickstart.springdata.models.Airport;
import org.couchbase.quickstart.springdata.models.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class MongoAirportRepositoryCustomImpl implements MongoAirportRepositoryCustom {

     @Autowired
     private MongoTemplate mongoTemplate;

     @Override
     public Page<Airport> findAll(Pageable pageable) {
          Query query = new Query().with(pageable);

          List<Airport> airports = mongoTemplate.find(query, Airport.class, "airport");
          long count = mongoTemplate.count(new Query(), "airport");

          return new PageImpl<>(airports, pageable, count);
     }

     @Override
     public Page<Route> getDirectConnections(String airportFaa, Pageable pageable) {

          List<AggregationOperation> operations = new ArrayList<>();

          operations.add(
                  Aggregation.match(Criteria.where("faa").is(airportFaa))
          );

          operations.add(
                  Aggregation.lookup("route", "faa", "sourceairport", "matchingRoutes")
          );

          operations.add(
                  Aggregation.unwind("matchingRoutes")
          );

          operations.add(
                  Aggregation.match(Criteria.where("matchingRoutes.stops").is(0))
          );

          operations.add(
                  Aggregation.replaceRoot("matchingRoutes")
          );

          // Count for pagination
          Aggregation countAgg = Aggregation.newAggregation(operations);
          long total = mongoTemplate.aggregate(countAgg, "airport", Route.class).getMappedResults().size();

          // Pagination
          operations.add(Aggregation.skip((long) pageable.getOffset()));
          operations.add(Aggregation.limit(pageable.getPageSize()));

          Aggregation finalAgg = Aggregation.newAggregation(operations);
          List<Route> routes = mongoTemplate.aggregate(finalAgg, "airport", Route.class).getMappedResults();

          return new PageImpl<>(routes, pageable, total);
     }
}
