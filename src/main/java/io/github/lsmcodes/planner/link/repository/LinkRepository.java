package io.github.lsmcodes.planner.link.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.lsmcodes.planner.link.model.Link;
import io.github.lsmcodes.planner.trip.model.Trip;

public interface LinkRepository extends JpaRepository<Link, UUID> {

        List<Link> findAllByTrip(Trip trip);

}