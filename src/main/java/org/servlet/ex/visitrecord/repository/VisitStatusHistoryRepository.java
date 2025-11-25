package org.servlet.ex.visitrecord.repository;
import org.servlet.ex.visitrecord.domain.VisitStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitStatusHistoryRepository extends JpaRepository<VisitStatusHistory, Long> {
}
