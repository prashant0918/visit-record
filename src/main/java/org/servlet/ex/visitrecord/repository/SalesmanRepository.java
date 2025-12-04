package org.servlet.ex.visitrecord.repository;
import org.servlet.ex.visitrecord.domain.Salesman;
import org.servlet.ex.visitrecord.domain.SalesmanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SalesmanRepository extends JpaRepository<Salesman, Long> {
    @Modifying
    @Query("UPDATE Salesman s SET s.status = :status WHERE s.id = :id AND s.status <> :status")
    int activateIfNot(@Param("status") SalesmanStatus status, @Param("id") Long id);
}

