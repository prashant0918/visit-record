package org.servlet.ex.visitrecord.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private Long recipientAdminId;     // nullable
    private Long recipientSalesmanId;  // nullable

    private String message;

    private boolean readFlag = false;

    private Instant createdAt = Instant.now();
}

