package org.servlet.ex.visitrecord.repository;
import org.servlet.ex.visitrecord.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}

