package org.servlet.ex.visitrecord.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class VisitStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Visit visit;

    @Enumerated(EnumType.STRING)
    private VisitStatus fromStatus;

    @Enumerated(EnumType.STRING)
    private VisitStatus toStatus;

    private Instant changedAt;

    @Enumerated(EnumType.STRING)
    private ActorType actorType;

    private Long actorId; // salesmanId/adminId if you want to track who

    private String reason;
}

