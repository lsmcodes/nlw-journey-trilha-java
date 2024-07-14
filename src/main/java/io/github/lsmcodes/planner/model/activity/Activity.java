package io.github.lsmcodes.planner.model.activity;

import java.time.LocalDateTime;
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
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID id;

        @Column(nullable = false)
        private String title;

        @Column(name = "occurs_at", nullable = false)
        private LocalDateTime occursAt;

        @ManyToOne
        @JoinColumn(name = "trip_id", nullable = false)
        private Trip trip;

        public Activity(String title, LocalDateTime occursAt, Trip trip) {
                this.title = title;
                this.occursAt = occursAt;
                this.trip = trip;
        }

}