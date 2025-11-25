package org.servlet.ex.visitrecord.service;
import lombok.RequiredArgsConstructor;
import org.servlet.ex.visitrecord.domain.Notification;
import org.servlet.ex.visitrecord.domain.NotificationType;
import org.servlet.ex.visitrecord.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void notifyAdmin(Long adminId, NotificationType type, String message) {
        Notification n = new Notification();
        n.setRecipientAdminId(adminId);
        n.setType(type);
        n.setMessage(message);
        notificationRepository.save(n);
    }

    public void notifySalesman(Long salesmanId, NotificationType type, String message) {
        Notification n = new Notification();
        n.setRecipientSalesmanId(salesmanId);
        n.setType(type);
        n.setMessage(message);
        notificationRepository.save(n);
    }
}

