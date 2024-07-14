package io.github.lsmcodes.planner.repository.link;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lsmcodes.planner.model.link.Link;
import io.github.lsmcodes.planner.model.trip.Trip;

public interface LinkRepository extends JpaRepository<Link, UUID> {

        List<Link> findAllByTrip(Trip trip);

}