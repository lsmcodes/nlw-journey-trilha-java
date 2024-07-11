package io.github.lsmcodes.planner.link.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.lsmcodes.planner.link.dto.LinkRequestPayload;
import io.github.lsmcodes.planner.link.model.Link;
import io.github.lsmcodes.planner.link.repository.LinkRepository;
import io.github.lsmcodes.planner.trip.model.Trip;

@Service
public class LinkService {

        @Autowired
        private LinkRepository repository;

        public UUID registerLinkToTrip(LinkRequestPayload payload, Trip trip) {
                Link link = new Link(payload.title(), payload.url(), trip);

                this.repository.save(link);
                return link.getId();
        }

        public List<Link> getLinksByTrip(Trip trip) {
                List<Link> links = this.repository.findAllByTrip(trip);
                return links;
        }

}