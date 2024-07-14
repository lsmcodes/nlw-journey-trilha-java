package io.github.lsmcodes.planner.dto.model.link;

import java.util.UUID;

import io.github.lsmcodes.planner.model.link.Link;

public record LinkData(UUID id, String title, String url) {

        public LinkData(Link link) {
                this(link.getId(), link.getTitle(), link.getUrl());
        }

}