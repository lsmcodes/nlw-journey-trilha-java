package io.github.lsmcodes.planner.participant.model;

import java.util.UUID;

import io.github.lsmcodes.planner.trip.model.Trip;
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
@Table(name = "participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Participant {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String email;

        @Column(name = "is_confirmed", nullable = false)
        private boolean isConfirmed;

        @ManyToOne
        @JoinColumn(name = "trip_id", nullable = false)
        private Trip trip;

        public Participant(String email, Trip trip) {
                this.name = "";
                this.email = email;
                this.isConfirmed = false;
                this.trip = trip;
        }

}