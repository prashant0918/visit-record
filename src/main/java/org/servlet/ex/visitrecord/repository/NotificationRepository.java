package org.servlet.ex.visitrecord.repository;

import org.servlet.ex.visitrecord.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}

