package org.servlet.ex.visitrecord.repository;
import org.servlet.ex.visitrecord.domain.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesmanRepository extends JpaRepository<Salesman, Long> {
}

