package org.servlet.ex.visitrecord.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Salesman salesman;

    @ManyToOne(optional = false)
    private Location location;

    private LocalDate scheduledDate;

    @Enumerated(EnumType.STRING)
    private VisitStatus status = VisitStatus.ASSIGNED;

    @Column(name = "priority", nullable = false)
    private VisitPriority priority;

    // timestamps for this execution
    private Instant arrivalTime;       // geofence-arrival time
    private Instant departureTime;     // geofence-departure time

    // reason if unavailable / skipped
    private String skipReason;
}

