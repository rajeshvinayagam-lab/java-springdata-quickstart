package org.couchbase.quickstart.springdata.services;

import java.util.Optional;

import org.couchbase.quickstart.springdata.models.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RouteService {

     Page<Route> getAllRoutes(Pageable pageable);

     Optional<Route> getRouteById(String id);

     Route saveRoute(Route route);

     void deleteRoute(String id);

     Route createRoute(Route route);

     Route updateRoute(String id, Route route);
}
