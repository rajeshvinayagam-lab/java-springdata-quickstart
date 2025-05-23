package org.couchbase.quickstart.springdata.repository.mongodb;

import java.util.List;

import org.couchbase.quickstart.springdata.models.Airline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class MongoAirlineRepositoryCustomImpl implements MongoAirlineRepositoryCustom {

     @Autowired
     private MongoTemplate mongoTemplate;

     public Page<Airline> findByCountry(String country, Pageable pageable) {
          Query query = new Query(Criteria.where("country").is(country))
                  .with(pageable);

          List<Airline> airlines = mongoTemplate.find(query, Airline.class, "airline");
          long count = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Airline.class, "airline");

          return new PageImpl<>(airlines, pageable, count);
     }

     public Page<Airline> findByDestinationAirport(String destinationAirport, Pageable pageable) {
          // Step 1: Get distinct airlineids from route
          Query routeQuery = new Query(Criteria.where("destinationairport").is(destinationAirport));
          List<String> airlineIds = mongoTemplate.findDistinct(routeQuery, "airlineid", "route", String.class);

          if (airlineIds.isEmpty()) {
               return new PageImpl<>(List.of(), pageable, 0);
          }

          // Step 2: Query airlines by those ids
          Query airlineQuery = new Query(Criteria.where("_id").in(airlineIds)).with(pageable);
          List<Airline> airlines = mongoTemplate.find(airlineQuery, Airline.class, "airline");

          // Step 3: Count total
          long count = mongoTemplate.count(
                  Query.of(airlineQuery).limit(-1).skip(-1),
                  Airline.class,
                  "airline"
          );

          return new PageImpl<>(airlines, pageable, count);
     }

}
