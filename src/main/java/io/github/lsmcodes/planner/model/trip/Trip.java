package io.github.lsmcodes.planner.model.trip;

import java.time.LocalDateTime;
import java.util.UUID;

import io.github.lsmcodes.planner.dto.model.trip.TripRequestPayload;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private String destination;

	@Column(name = "starts_at", nullable = false)
	private LocalDateTime startsAt;

	@Column(name = "ends_at", nullable = false)
	private LocalDateTime endsAt;

	@Column(name = "is_confirmed", nullable = false)
	private Boolean isConfirmed;

	@Column(name = "owner_name", nullable = false)
	private String ownerName;

	@Column(name = "owner_email", nullable = false)
	private String ownerEmail;

	public Trip(TripRequestPayload payload) {
		this.destination = payload.destination();
		this.startsAt = payload.startsAt();
		this.endsAt = payload.endsAt();
		this.isConfirmed = false;
		this.ownerName = payload.ownerName();
		this.ownerEmail = payload.ownerEmail();
	}

}