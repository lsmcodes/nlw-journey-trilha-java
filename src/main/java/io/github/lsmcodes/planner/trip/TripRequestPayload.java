package io.github.lsmcodes.planner.trip;

import java.util.List;

public record TripRequestPayload(String destination, String startsAt, String endsAt, List<String> emailsToInvite,
                String ownerName, String ownerEmail) {

}