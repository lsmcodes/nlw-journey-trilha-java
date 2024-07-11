package io.github.lsmcodes.planner.link.dto;

import java.util.UUID;

import io.github.lsmcodes.planner.link.model.Link;

public record LinkData(UUID id, String title, String url) {

        public LinkData(Link link) {
                this(link.getId(), link.getTitle(), link.getUrl());
        }

}