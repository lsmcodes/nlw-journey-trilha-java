package io.github.lsmcodes.planner.model.link;

import java.util.UUID;

import io.github.lsmcodes.planner.model.trip.Trip;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "links")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Link {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID id;

        @Column(nullable = false)
        private String title;

        @Column(nullable = false)
        private String url;

        @ManyToOne
        @JoinColumn(name = "trip_id", nullable = false)
        private Trip trip;

        public Link(String title, String url, Trip trip) {
                this.title = title;
                this.url = url;
                this.trip = trip;
        }

}