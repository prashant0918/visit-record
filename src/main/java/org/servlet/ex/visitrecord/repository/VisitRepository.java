package org.servlet.ex.visitrecord.repository;

import org.servlet.ex.visitrecord.domain.Visit;
import org.servlet.ex.visitrecord.domain.VisitStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    List<Visit> findBySalesmanIdAndScheduledDateOrderByPriorityDesc(
            Long salesmanId, LocalDate date);

    Optional<Visit> findFirstBySalesmanIdAndScheduledDateAndStatusInOrderByPriorityDesc(
            Long salesmanId,
            LocalDate date,
            List<VisitStatus> statuses
    );
}
